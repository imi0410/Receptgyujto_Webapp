package hu.unideb.inf.receptgyujto.service.impl;

import hu.unideb.inf.receptgyujto.data.repository.ReceptRepository;
import hu.unideb.inf.receptgyujto.service.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    final ReceptRepository receptRepository;

    public SecurityServiceImpl(ReceptRepository receptRepository) {
        this.receptRepository = receptRepository;
    }

    @Override
    public Boolean isReceptOwner(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return receptRepository.findById(id).map(receptEntity -> {
                    return receptEntity.getFelhasznalo().getFelhasznalonev().equals(authentication.getName());})
                .orElse(false);
    }
}
