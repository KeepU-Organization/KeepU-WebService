package com.keepu.webAPI.service;

import com.keepu.webAPI.dto.request.CreateSavingGoalRequest;
import com.keepu.webAPI.dto.response.SavingGoalResponse;
import com.keepu.webAPI.exception.NotFoundException;
import com.keepu.webAPI.mapper.SavingGoalsMapper;
import com.keepu.webAPI.model.SavingGoals;
import com.keepu.webAPI.model.User;
import com.keepu.webAPI.repository.SavingGoalsRepository;
import com.keepu.webAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SavingGoalsService {

    private final SavingGoalsRepository savingGoalsRepository;
    private final UserRepository userRepository;
    private final SavingGoalsMapper savingGoalsMapper;

    @Transactional
    public SavingGoalResponse createSavingGoal(CreateSavingGoalRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        SavingGoals savingGoals = savingGoalsMapper.toSavingGoalsEntity(request, user);
        SavingGoals savedSavingGoals = savingGoalsRepository.save(savingGoals);

        return savingGoalsMapper.toSavingGoalResponse(savedSavingGoals);
    }
}