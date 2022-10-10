package com.zerobase.zerolms.main.repository;

import com.zerobase.zerolms.main.dto.HistoryDto;
import com.zerobase.zerolms.main.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogHistoryRepository extends JpaRepository<LoginHistory,String> {





}

