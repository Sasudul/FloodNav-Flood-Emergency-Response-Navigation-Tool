# PDSA-CW

# FloodNav | Disaster Response Routing System

This project is Developed as part of the Programming, Data Structures, and Algorithms (PDSA) coursework at the National Institute of Business Management (NIBM). **FloodNav** is a specialized decision-support engine designed for emergency rescuers. It addresses the "Last Mile" navigation problem in flooded environments by combining priority-based scheduling with dynamic graph-based routing.

# Project Overview
During flash floods, standard navigation tools (GPS) often fail because they lack real-time data on road traversability and cannot prioritize victims based on urgency. FloodNav-Lite centralizes this logic into a standalone system that operates independently of third-party routing engines.

**Key Objectives:**
- Dynamic Topology: Model road networks as graphs where edges can be disabled/enabled in real-time.
- Priority-Driven Dispatch: Use a Max Heap to automate the decision-making process for rescue order.
- Visual Validation: Decouple logic from UI by using Google Maps only as a canvas for the system's internal pathfinding results.

**Tech Stack**

Backend: Java 17, Spring Boot (REST API)

Frontend: React 18, TypeScript, Tailwind CSS

Data Structures: * Map<Integer, List<Edge>> for Graph Adjacency List.
Custom PriorityQueue implementation for the Max Heap.
