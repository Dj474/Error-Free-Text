package org.errorfreetext.service.speller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.errorfreetext.entity.task.Task;
import org.errorfreetext.other.enums.TaskStatus;
import org.errorfreetext.repository.task.TaskRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class CorrectionScheduler {

    private final TaskRepository taskRepository;
    private final CorrectionService correctionService;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void processPendingTasks() {
        List<Task> pendingTasks = taskRepository.findByStatus(TaskStatus.NEW);

        for (Task task : pendingTasks) {
            try {
                task.setStatus(TaskStatus.PROCESSING);
                taskRepository.save(task);

                String corrected = correctionService.correctText(task.getSourceText(), task.getLanguage());

                task.setCorrectedText(corrected);
                task.setStatus(TaskStatus.COMPLETED);
            } catch (Exception e) {
                task.setStatus(TaskStatus.ERROR);
                task.setErrorMessage(e.getMessage());
            } finally {
                taskRepository.save(task);
            }
        }
    }
}