//package com.kj.config;
//
//import com.kj.batch.CustomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.adapter.ItemReaderAdapter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@RequiredArgsConstructor
//@Configuration
//public class BatchConfig {
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager tm;
//
//    @Bean
//    public Job job() {
//        return new JobBuilder("batchJob",jobRepository)
////                .incrementer(new RunIdIncrementer())
//                .start(step1())
//                .build();
//    }
//
//    @Bean
//    public Step step1() {
//        return new StepBuilder("step1",jobRepository)
//                .chunk(10,tm)
//                .reader(customItemReader())
//                .writer(customItemWriter())
//                .build();
//    }
//
//    @Bean
//    public ItemReader<?> customItemReader() {
//        ItemReaderAdapter reader = new ItemReaderAdapter();
//        reader.setTargetObject(customService());
//        reader.setTargetMethod("joinCustomer");
//        return reader;
//
//    }
//
//    private CustomService<String> customService(){
//        return new CustomService<>();
//    }
//
//    @Bean
//    public ItemWriter<? super Object> customItemWriter() {
//        return items -> {
//            System.out.println(items);
//        };
//    }
//}
