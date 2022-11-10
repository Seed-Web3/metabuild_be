package com.seed.careerhub.endpoint;

import com.seed.careerhub.domain.Job;
import com.seed.careerhub.jpa.JobRepository;
import com.seed.careerhub.model.EventDTO;
import com.seed.careerhub.model.JobRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/job")
public class JobEndpoint {

    private final JobRepository jobRepository;

    public JobEndpoint(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * @param jobRequest jobRequest
     * @return Job
     */
    @Operation(summary = "Saves job post")
    @PostMapping()
    public Job save(@RequestBody JobRequest jobRequest) {
        log.debug("Save a new Job {}", jobRequest);
        Job job = new Job();
        job.setCompany(jobRequest.getCompany());
        if (jobRequest.getBounty() != null) {
            job.setBountyAmount((Integer) jobRequest.getBounty().get("amount"));
            job.setBountyCurrency((String) jobRequest.getBounty().get("currency"));
        }
        job.setDescription(jobRequest.getDescription());
        job.setEmail(jobRequest.getEmail());
        job.setLocation(jobRequest.getLocation());
        job.setLogo(jobRequest.getLogo());
        if (jobRequest.getSalary() != null) {
            job.setSalaryCurrency((String) jobRequest.getSalary().get("currency"));
            job.setSalaryMin((Integer) jobRequest.getSalary().get("min"));
            job.setSalaryMax((Integer) jobRequest.getSalary().get("max"));
        }
        job.setSkills(String.join(" ", jobRequest.getSkills()));
//        job.setSocials(jobRequest.getSocials());
        job.setTitle(jobRequest.getTitle());
        return jobRepository.save(job);
    }


    /**
     * Gets all jobs.
     *
     * @return list of jobs
     */
    @Operation(summary = "Gets jobs")
    @GetMapping("all")
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
}
