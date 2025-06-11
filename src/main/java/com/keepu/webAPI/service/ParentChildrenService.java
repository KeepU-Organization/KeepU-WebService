package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateParentChildrenRequest;
import com.keepu.webAPI.dto.response.ParentChildrenResponse;
import com.keepu.webAPI.dto.response.UserResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.ParentChildrenMapper;
import com.keepu.webAPI.model.Children;
import com.keepu.webAPI.model.Parent;
import com.keepu.webAPI.model.ParentChildren;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.ChildrenRepository;
import com.keepu.webAPI.repository.ParentChildrenRepository;
import com.keepu.webAPI.repository.ParentRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParentChildrenService {

    private final ParentChildrenRepository parentChildrenRepository;
    private final ParentRepository parentRepository;
    private final ChildrenRepository childrenRepository;
    private final ParentChildrenMapper parentChildrenMapper;
    private final UserRepository userRepository;
    @Transactional
    public ParentChildrenResponse createParentChildren(CreateParentChildrenRequest request) {
        Parent parent = parentRepository.findById(request.parentId())
                .orElseThrow(() -> new NotFoundException("Parent not found"));

        Children child = childrenRepository.findById(request.childId())
                .orElseThrow(() -> new NotFoundException("Child not found"));

        ParentChildren parentChildren = parentChildrenMapper.toParentChildrenEntity(request, parent, child);
        ParentChildren savedParentChildren = parentChildrenRepository.save(parentChildren);

        return parentChildrenMapper.toParentChildrenResponse(savedParentChildren);
    }
    @Transactional(readOnly = true)
    public List<UserResponse.ChildSummary> getChildrenSummaryByParentId(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new NotFoundException("Parent not found"));

        List<Number> childrenIds = parentChildrenRepository.findChildrenByParent(parent.getUser().getId());

        List<UserResponse.ChildSummary> childrenList = childrenIds.stream()
                .map(id -> {
                    Optional<User> childOpt = userRepository.findById(id.longValue());
                    Optional<Children> childrenOpt = childrenRepository.findById(id.longValue());

                    if (childOpt.isPresent() && childrenOpt.isPresent()) {
                        User child = childOpt.get();
                        Children children = childrenOpt.get();

                        return new UserResponse.ChildSummary(
                                child.getId(),
                                child.getName(),
                                child.getLastNames(),
                                child.getEmail(),
                                children.getAge()
                        );
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();


        if (childrenList.isEmpty()) {
            throw new NotFoundException("No children found for this parent");
        }
        return childrenList;
    }
}