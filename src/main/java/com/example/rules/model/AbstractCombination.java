package com.example.rules.model;

import lombok.*;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractCombination {
    private Area from;
    private Area to;
    private List<Area> stops;

    public static Area parseArea(String area) {
        if (StringUtils.hasLength(area)) {
            return Area.valueOf(Area.class, area);
        } else
            return null;
    }

}
