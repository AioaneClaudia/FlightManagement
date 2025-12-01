package com.example.flightmanagement.repository;

import com.example.flightmanagement.model.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeBoardRepository extends JpaRepository<NoticeBoard, String> {
}
