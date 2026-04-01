package com.mytodo;

import java.util.List;

public class ReorderRequest {
    private Long dayId;
    private List<Long> orderedIds;

    public Long getDayId() { return dayId; }
    public void setDayId(Long dayId) { this.dayId = dayId; }
    public List<Long> getOrderedIds() { return orderedIds; }
    public void setOrderedIds(List<Long> orderedIds) { this.orderedIds = orderedIds; }
}
