package com.keepu.webAPI.mapper;

import com.keepu.webAPI.dto.request.CreateSavingGoalRequest;
import com.keepu.webAPI.dto.response.SavingGoalResponse;
import com.keepu.webAPI.model.SavingGoals;
import com.keepu.webAPI.model.User;
import org.springframework.stereotype.Component;

@Component
public class SavingGoalsMapper {

    public SavingGoalResponse toSavingGoalResponse(SavingGoals savingGoals) {
        if (savingGoals == null) {
            return null;
        }
        return new SavingGoalResponse(
                savingGoals.getId(),
                savingGoals.getName(),
                savingGoals.getTargetAmount(),
                savingGoals.getCurrentAmount(),
                savingGoals.isCompleted(),
                savingGoals.getUser().getId()
        );
    }

    public SavingGoals toSavingGoalsEntity(CreateSavingGoalRequest request, User user) {
        if (request == null || user == null) {
            return null;
        }

        SavingGoals savingGoals = new SavingGoals();
        savingGoals.setName(request.name());
        savingGoals.setTargetAmount(request.targetAmount());
        savingGoals.setCurrentAmount(0.0);
        savingGoals.setCompleted(false);
        savingGoals.setUser(user);
        return savingGoals;
    }
}