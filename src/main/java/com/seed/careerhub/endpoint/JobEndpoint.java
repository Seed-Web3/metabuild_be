package com.seed.careerhub.endpoint;

import com.seed.careerhub.model.JobRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/job")
public class JobEndpoint {

    /**
     * @param jobRequest jobRequest
     * @return Job
     */
    @Operation(summary = "Saves job post")
    @PostMapping()
    public String save(@RequestBody JobRequest jobRequest) {
        log.debug("Save a new Job {}", jobRequest);
//        Job job = new Job(jobRequest.getTitle(),
//                jobRequest.getDescription(),
//        return jobRepository.save(job);
        return "OK";
    }

}
