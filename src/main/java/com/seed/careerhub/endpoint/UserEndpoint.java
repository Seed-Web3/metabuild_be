package com.seed.careerhub.endpoint;

import com.seed.careerhub.domain.User;
import com.seed.careerhub.exception.DataNotFound;
import com.seed.careerhub.jpa.UserRepository;
import com.seed.careerhub.model.UserRequest;
import com.seed.careerhub.util.EndpointUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEndpoint {

    private final UserRepository userRepository;

    /**
     * Constructor.
     *
     * @param userRepository userRepository
     */
    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gets details of logged-in user.
     *
     * @return User
     */
    @Operation(summary = "Gets details of logged-in user")
    @GetMapping
    public User loggedInUser() {
        User user= getUser();
        if (user == null) {
            throw new DataNotFound();
        }
        return user;
    }

    /**
     * Gets all user details
     *
     * @return list of all users
     */
    @Operation(summary = "Gets all user details")
    @GetMapping("all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Updates user details.
     * Wallet addresses are read-only.
     *
     * @param userRequest userRequest
     * @return the updated User
     */
    @Operation(summary = "Updates user details (except addresses)")
    @PostMapping
    public User updateUser(@RequestBody UserRequest userRequest) {
        User user = getUser();
        if (user == null) {
            throw new DataNotFound();
        }

        user.setName(userRequest.getName());
        user.setHandle(userRequest.getHandle());
        user.setBio(userRequest.getBio());
//        user.setNearAddress(userRequest.get);
//        user.setEmail(userRequest.get);
        user.setTwitter(userRequest.getTwitter());
        user.setGithub(userRequest.getGithub());
        user.setLinkedin(userRequest.getLinkedin());
        user.setWebsite(userRequest.getWebsite());
        user.setMainSkill(userRequest.getMainSkill());
        user.setOpenToJobOpportunity(userRequest.isOpenToJobOpportunity());
        user.setOpenToRemoteJob(userRequest.isOpenToRemoteJob());
        user.setReceiveNewJobEmail(userRequest.isReceiveNewJobEmail());
        user.setShowPublicAddress(userRequest.isShowPublicAddress());
        user.setShowLocation(userRequest.isShowLocation());

        return userRepository.save(user);
    }

    /**
     * Retrieves the logged-in user object or null if not found.
     *
     * @return User
     */
    private User getUser() {
        String address = EndpointUtil.getLoggedInAddress();
        if ("anonymousUser".equalsIgnoreCase(address)) {
            return null;
        } else if (isEmailAddress(address)) {
            return userRepository.findOneByEmail(address).orElseThrow(DataNotFound::new);
        } else {
            return userRepository.findByNearAddress(address);
        }

    }

    private boolean isEmailAddress(String address) {
        return address != null && address.contains("@");
    }

}
