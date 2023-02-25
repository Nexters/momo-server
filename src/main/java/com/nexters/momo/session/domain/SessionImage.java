package com.nexters.momo.session.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 세션 이미지 관련 엔티티 클래스입니다.
 *
 * @author CHO Min Ho
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionImage {

    @Id
    @GeneratedValue
    @Column(name = "session_image_id")
    private Long id;

    @Column(name = "session_id")
    private Long sessionId;

    private String url;

    private int imageOrder;

    private SessionImage(Long sessionId, String url, int imageOrder) {
        this.sessionId = sessionId;
        this.url = url;
        this.imageOrder = imageOrder;
    }

    /**
     * SessionImage 엔티티 생성 메서드입니다.
     * @param sessionId 해당 session ID
     * @param url 이미지 URL
     * @param imageOrder 이미지 순서
     * @return 생성된 SessionImage 엔티티
     */
    public static SessionImage createSessionImage(Long sessionId, String url, int imageOrder) {
        return new SessionImage(sessionId, url, imageOrder);
    }
}
