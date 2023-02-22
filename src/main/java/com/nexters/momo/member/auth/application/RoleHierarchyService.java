package com.nexters.momo.member.auth.application;

import com.nexters.momo.member.auth.domain.RoleHierarchy;
import com.nexters.momo.member.auth.domain.RoleHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    @Transactional(readOnly = true)
    public String AllHierarchyToString() {
        List<RoleHierarchy> rolesHierarchy = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> iterator = rolesHierarchy.iterator();
        StringBuffer concatedRoles = new StringBuffer();
        while (iterator.hasNext()) {
            RoleHierarchy model = iterator.next();
            if (model.getParentName() != null) {
                concatedRoles.append(model.getParentName().getChildName());
                concatedRoles.append(" > ");
                concatedRoles.append(model.getChildName());
                concatedRoles.append("\n");
            }
        }

        return concatedRoles.toString();
    }
}
