package com.floodnav.floodnav_backend.algorithm.heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class PriorityCluster implements Comparable<PriorityCluster> {

    private final String clusterId;

    // backing list (optional but useful for auditing/dispatch UI)
    private final List<SOSRequest> requests = new ArrayList<>();

    private int totalPeople;
    private double averageSeverity;
    private double distanceFromBase;

    // cached
    private double priorityScore;


    public PriorityCluster(String clusterId, double distanceFromBase) {
        this.clusterId = Objects.requireNonNull(clusterId, "clusterId must not be null");
        this.distanceFromBase = distanceFromBase;
        recompute();
    }


    public void addRequest(SOSRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        requests.add(request);
        recompute();
    }



    public void addRequests(List<SOSRequest> reqs) {
        if (reqs == null || reqs.isEmpty()) return;
        for (SOSRequest r : reqs) {
            if (r != null) requests.add(r);
        }
        recompute();
    }


    public void setDistanceFromBase(double distanceFromBase) {
        this.distanceFromBase = distanceFromBase;
        recompute();
    }

    public String getClusterId() {
        return clusterId;
    }

    public List<SOSRequest> getRequests() {
        return Collections.unmodifiableList(requests);
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

    /**
     * The computed priority score used by the heap ordering.
     */
    public double getPriorityScore() {
        return priorityScore;
    }


    @Override
    public int compareTo(PriorityCluster other) {
        if (other == null) return -1;

        int byScore = Double.compare(other.priorityScore, this.priorityScore); // descending
        if (byScore != 0) return byScore;

        int byPeople = Integer.compare(other.totalPeople, this.totalPeople); // descending
        if (byPeople != 0) return byPeople;

        int bySeverity = Double.compare(other.averageSeverity, this.averageSeverity); // descending
        if (bySeverity != 0) return bySeverity;

        return Double.compare(this.distanceFromBase, other.distanceFromBase); // ascending (nearer wins)
    }

    @Override
    public String toString() {
        return "PriorityCluster{" +
                "clusterId='" + clusterId + '\'' +
                ", totalPeople=" + totalPeople +
                ", averageSeverity=" + averageSeverity +
                ", distanceFromBase=" + distanceFromBase +
                ", priorityScore=" + priorityScore +
                '}';
    }

    private void recompute() {
        int peopleSum = 0;
        double severitySum = 0.0;

        for (SOSRequest r : requests) {
            peopleSum += Math.max(0, r.getPeopleCount());
            severitySum += Math.max(0.0, r.getSeverity());
        }

        this.totalPeople = peopleSum;
        this.averageSeverity = requests.isEmpty() ? 0.0 : (severitySum / requests.size());

        // Priority formula:
        // (totalPeople * 10) + (averageSeverity * 5) - (distanceFromBase * 0.1)
        this.priorityScore = (this.totalPeople * 10.0)
                + (this.averageSeverity * 5.0)
                - (this.distanceFromBase * 0.1);
    }
}
