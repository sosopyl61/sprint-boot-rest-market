package by.rymtsou.service;

import by.rymtsou.model.Security;
import by.rymtsou.model.User;
import by.rymtsou.repository.SecurityRepository;
import by.rymtsou.model.dto.RegistrationRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class SecurityService {

    public final SecurityRepository securityRepository;

    @Autowired
    public SecurityService(SecurityRepository securityRepository) {
        this.securityRepository = securityRepository;
    }

    public Optional<Security> getSecurityById(Long id) {
        return securityRepository.getSecurityById(id);
    }

    public Boolean registration(RegistrationRequestDto requestDto) {
        try {
            if (securityRepository.isLoginUsed(requestDto.getLogin())) {
                return false;
            }
            return securityRepository.registration(requestDto);
        } catch (SQLException e) {
            return false;
        }
    }

    public Optional<Security> updateSecurity(Security security) {
        Boolean isUserUpdated = securityRepository.updateSecurity(security);
        if (isUserUpdated) {
            return getSecurityById(security.getId());
        }
        return Optional.empty();
    }

    public Boolean deleteSecurity(Long id) {
        return securityRepository.deleteSecurity(id);
    }
}
