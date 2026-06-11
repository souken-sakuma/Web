package com.example.demo.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
    	
    	if ("ADMIN".equals(user.getRole()) || "ROLE_ADMIN".equals(user.getRole())) {
            user.setMemberNumber("A0000");
        } else {
	    	String memberNumber = generateMemberNumber();
	    	user.setMemberNumber(memberNumber);
        }
    	
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
	
	if (user.getRole() == null) {
        user.setRole("USER");
    }

	userRepository.save(user);
    }
    
    private String generateMemberNumber() {
    	User lastUser = userRepository.findTopByOrderByIdDesc();
    	
    	long nextNumber = 1;
    	
    	if (lastUser != null && lastUser.getMemberNumber() != null) {
    		String last = lastUser.getMemberNumber().substring(1);
    		nextNumber = Long.parseLong(last) + 1;
    	}
    	
    	if (nextNumber > 9999) {
    	    throw new IllegalStateException("会員番号が上限に達しました");
    	}

    	
    	return String.format("A%04d", nextNumber);
    }
    
    
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
