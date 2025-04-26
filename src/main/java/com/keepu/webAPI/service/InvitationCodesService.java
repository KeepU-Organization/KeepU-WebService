package com.keepu.webAPI.service;

import com.keepu.webAPI.model.InvitationCodes;
import com.keepu.webAPI.repository.InvitationCodesRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InvitationCodesService {

    private final InvitationCodesRepository repo;

    public InvitationCodesService(InvitationCodesRepository repo) {
        this.repo = repo;
    }

    public List<InvitationCodes> findAll() {
        return repo.findAll();
    }

    public InvitationCodes findById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    public InvitationCodes save(InvitationCodes code) {
        return repo.save(code);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
