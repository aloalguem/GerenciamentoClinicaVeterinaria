package br.senai.sp.odemarfim.service;

import br.senai.sp.odemarfim.model.Feedback;
import br.senai.sp.odemarfim.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackService(FeedbackRepository repository) {
        this.repository = repository;
    }

    public Feedback save(Feedback feedback) {
        return repository.save(feedback);
    }

    public List<Feedback> findAll() {
        return repository.findAll();
    }
}
