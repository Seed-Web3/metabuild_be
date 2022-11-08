package com.seed.careerhub.endpoint;

import com.seed.careerhub.domain.Skill;
import com.seed.careerhub.exception.DataNotFound;
import com.seed.careerhub.jpa.SkillRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
public class SkillsEndpoint {

    private static final int MIN_SKILL_LENGTH = 3;
    private final SkillRepository skillRepository;

    public SkillsEndpoint(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Operation(summary = "Get all skill tags")
    @GetMapping
    public ResponseEntity<?> getAllSkills() {
        try {
            List<Skill> skills = skillRepository.findAll(Sort.by("name"));
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Operation(summary = "Autocomplete skill")
    @GetMapping("{tag}")
    public ResponseEntity<?> findSkills(@PathVariable String tag) {
        try {
            if (tag.trim().length() < MIN_SKILL_LENGTH) {
                throw new DataNotFound();
            }
            List<Skill> skills = skillRepository.findAllByNameContainingIgnoreCase(tag.trim().toLowerCase());
            return ResponseEntity.ok(skills);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Operation(summary = "Create a skill tag")
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody Skill skill) {
        try {
            Skill _skill = skillRepository.findOneByName(skill.getName())
                    .orElseGet(() -> skillRepository.save(new Skill(skill.getName())));
            return new ResponseEntity<>(_skill, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
