package com.floodnav.floodnav_backend.algorithm.heap;

import com.floodnav.floodnav_backend.model.SosRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cluster of SOS requests with calculated priority.
 * Used in Max Heap (PriorityQueue) to prioritize rescue missions.
 *
 * Priority Formula: (totalPeople * 10) + (averageSeverity * 5) - (distanceFromBase * 0.1)
 * Higher priority value = more urgent rescue
 */
public class PriorityCluster implements Comparable<PriorityCluster> {
    private final Long clusterId;
    private final List<SosRequest> requests;
    private final double centerLatitude;
    private final double centerLongitude;
    private final int totalPeople;
    private final double averageSeverity;
    private final double distanceFromBase;
    private final double priorityScore;

    public PriorityCluster(Long clusterId, List<SosRequest> requests,
                           double centerLatitude, double centerLongitude,
                           double distanceFromBase) {
        this.clusterId = clusterId;
        this.requests = new ArrayList<>(requests);
        this.centerLatitude = centerLatitude;
        this.centerLongitude = centerLongitude;
        this.distanceFromBase = distanceFromBase;

        // Calculate aggregate metrics
        this.totalPeople = requests.stream()
                .mapToInt(SosRequest::getAffectedPeople)
                .sum();

        this.averageSeverity = requests.stream()
                .mapToInt(r -> getSeverityWeight(r.getSeverityLevel()))
                .average()
                .orElse(0.0);

        // Calculate priority score using the specified formula
        this.priorityScore = (totalPeople * 10.0) +
                (averageSeverity * 5.0) -
                (distanceFromBase * 0.1);
    }

    /**
     * Convert severity level string to numeric weight
     */
    private int getSeverityWeight(String severityLevel) {
        switch (severityLevel.toUpperCase()) {
            case "CRITICAL": return 4;
            case "HIGH": return 3;
            case "MEDIUM": return 2;
            case "LOW": return 1;
            default: return 1;
        }
    }

    /**
     * Comparator for Max Heap behavior.
     * Returns reversed comparison to make PriorityQueue act as Max Heap.
     */
    @Override
    public int compareTo(PriorityCluster other) {
            return Double.compare(other.priorityScore, this.priorityScore);
    }

    // Getters
    public Long getClusterId() {
        return clusterId;
    }

    public List<SosRequest> getRequests() {
        return requests;
    }

    public double getCenterLatitude() {
        return centerLatitude;
    }

    public double getCenterLongitude() {
        return centerLongitude;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public double getAverageSeverity() {
        return averageSeverity;
    }

    public double getDistanceFromBase() {
        return distanceFromBase;
    }

    public double getPriorityScore() {
        return priorityScore;
    }

    public int getRequestCount() {
        return requests.size();
    }

    @Override
    public String toString() {
        return String.format("Cluster{id=%d, requests=%d, people=%d, priority=%.2f}",
                clusterId, requests.size(), totalPeople, priorityScore);
    }
}