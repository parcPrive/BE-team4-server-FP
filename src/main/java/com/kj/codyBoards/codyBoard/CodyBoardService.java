package com.kj.codyBoards.codyBoard;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kj.codyBoards.codyBoard.Repository.CodyBoardRepository;
import com.kj.codyBoards.codyBoard.dto.CodyBoardInputDto;
import com.kj.codyBoards.codyBoard.dto.CodyBoardListReturnDto;
import com.kj.codyBoards.codyBoard.dto.CodyBoardSearchCondition;
import com.kj.codyBoards.codyBoard.entiry.CodyBoard;
import com.kj.config.S3Config;
import com.kj.member.entity.Member;
import com.kj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodyBoardService {
    private final CodyBoardRepository codyBoardRepository;
    private final MemberRepository memberRepository;
//    private final RedisTemplate redisTemplate;


    private final S3Config s3Config;
    @Value("${file.path}")
    private String myLocalFolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void insert(CodyBoardInputDto codyBoardInputDto) throws IOException {
        // s3에 이미지 올리고 이미지 주소 반환받는다.
        List<String> codyImageUrls = s3ImageUpload(codyBoardInputDto);
        // 반환받은 주소랑 같이 데이터베이스에 저장한다.
//        Optional<Member> findMember = memberRepository.findByNickName(codyBoardInputDto.getUserName());
        Optional<Member> findMember = memberRepository.findByUserId(codyBoardInputDto.getUserName());
        log.info("댓글생성시.. =-==>>> {}", findMember.get().getEmail());
        if(findMember.isPresent()){
            CodyBoard insertCodyBoard = new CodyBoard(codyBoardInputDto, codyImageUrls, findMember.get());
            codyBoardRepository.save(insertCodyBoard);
        }

    }

    @Transactional
    public List<String> s3ImageUpload(CodyBoardInputDto codyBoardInputDto) throws IOException {
        String localLocation = myLocalFolder + codyBoardInputDto.getCodyBoardBucket() + "/";
        File folder = new File(localLocation);
        List<String> codyImages = new ArrayList<>();
        for(MultipartFile codyImge : codyBoardInputDto.getFile()){
            String codyImageName = codyImge.getOriginalFilename();
            log.info("이미지 이름 ==>> {}", codyImageName);
            codyImages.add(UUID.randomUUID() + codyBoardInputDto.getUserName() + codyImageName.substring(codyImageName.indexOf(".")));
        }
        if(!folder.exists()){
            folder.mkdir();
            log.info("폴더생성");
        }else{
            log.info("폴더실패");
        }
        List<String> codyImageUrls = new ArrayList<>();
        for(int i = 0; i < codyImages.size(); i++){
            String localPath = localLocation + codyImages.get(i);
            File localFile = new File(localPath);
            codyBoardInputDto.getFile().get(i).transferTo(localFile);
            s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket + "/codyimage/" + codyBoardInputDto.getCodyBoardBucket(), codyImages.get(i), localFile).withCannedAcl(CannedAccessControlList.PublicRead));
            String codyImageUrl = s3Config.amazonS3Client().getUrl(bucket + "/codyimage/" + codyBoardInputDto.getCodyBoardBucket(),codyImages.get(i)).toString();
            log.info("codyImageUrl ===>>> {}", codyImageUrl);
            codyImageUrls.add(codyImageUrl);
        }
        while (folder.exists()){
            File[] folderList = folder.listFiles();
            for(int i = 0; i < folderList.length; i++){
                folderList[i].delete();
                log.info("파일삭제");
            }
            if(folderList.length == 0 && folder.isDirectory()){
                folder.delete();
                log.info("폴더삭제");
            }
        }
        return codyImageUrls;

    }

    public PageImpl<CodyBoardListReturnDto> findListCodyBoard(int page, CodyBoardSearchCondition codyBoardSearchCondition) {
        Pageable pageable = PageRequest.of(page -1, 12);

        PageImpl<CodyBoardListReturnDto> codyBoardList = codyBoardRepository.findListCodyBoard(pageable,codyBoardSearchCondition);
        return codyBoardList;
    }
    @Transactional
    public CodyBoard findByCodyBoard(Long codyBoardId) {
       CodyBoard aaa = codyBoardRepository.findByCodyBoard(codyBoardId);
//       log.info("메법버ㅓ버버버버버 =====.>>>>> {} ",aaa.getCodyBoardComments().get(0).getMember().getUserName());
        return  aaa;
    }
}
