package com.nexters.momo.session.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class Point {
    private double latitude;
    private double longitude;

    public static Point of(double latitude, double longitude) {
        return new Point(latitude, longitude);
    }
}
