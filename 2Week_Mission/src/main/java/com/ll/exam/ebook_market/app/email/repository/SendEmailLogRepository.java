package com.ll.exam.ebook_market.app.email.repository;

import com.ll.exam.ebook_market.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailLogRepository extends JpaRepository<SendEmailLog, Long> {
}
