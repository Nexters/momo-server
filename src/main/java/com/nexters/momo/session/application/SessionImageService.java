package com.nexters.momo.session.application;

import com.nexters.momo.file.util.FileUtil;
import com.nexters.momo.session.domain.SessionImage;
import com.nexters.momo.session.domain.SessionImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 세션 이미지 관련 서비스 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SessionImageService {

    private final SessionImageRepository sessionImageRepository;
    private final FileUtil fileUtil;
    private final String category = "session";

    /**
     * 세션 이미지를 업로드하는 메서드입니다.
     * @param id 세션 ID
     * @param files 이미지 리스트
     */
    public void createSessionImage(Long id, List<MultipartFile> files) {

        if (files != null) {
            for (int i = 0; i < files.size(); i++) {
                sessionImageRepository.save(
                        SessionImage.createSessionImage(id, fileUtil.uploadFile(category, files.get(i)), i));
            }
        }
    }

    /**
     * 세션 이미지를 삭제하는 메서드입니다.
     * @param id 세션 ID
     */
    public void deleteSessionImage(Long id) {
        sessionImageRepository.deleteBySessionId(id);
    }

    /**
     * 특정 세션의 이미지 URL 리스트를 반환합니다.
     * @param id 세션 ID
     * @return 세션 URL 리스트
     */
    public List<String> getSessionImageList(Long id) {
        List<String> result = new ArrayList<>();

        for (SessionImage sessionImage : sessionImageRepository.findBySessionId(id)) {
            result.add(sessionImage.getUrl());
        }

        return result;
    }

}
