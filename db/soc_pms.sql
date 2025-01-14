-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 22, 2024 at 03:46 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `soc_pms`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_audit_log`
--

CREATE TABLE `tbl_audit_log` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action` varchar(50) NOT NULL,
  `details` text DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_audit_log`
--

INSERT INTO `tbl_audit_log` (`log_id`, `user_id`, `action`, `details`, `ip_address`, `timestamp`) VALUES
(1, 3, 'SUBMIT_PAPERWORK', 'Submitted PPW/2024/001', '192.168.1.100', '2024-01-15 01:30:00'),
(2, 4, 'APPROVE_PAPERWORK', 'Approved PPW/2024/001', '192.168.1.101', '2024-01-16 06:20:00'),
(3, 5, 'APPROVE_PAPERWORK', 'Approved PPW/2024/001', '192.168.1.102', '2024-01-17 02:15:00'),
(4, 6, 'SUBMIT_PAPERWORK', 'Submitted PPW/2024/003', '192.168.1.103', '2024-01-22 05:15:00'),
(5, 2, 'SUBMIT_PAPERWORK', 'Submitted PPW/2024/001', '192.168.1.100', '2024-01-10 01:00:00'),
(6, 5, 'APPROVE_PAPERWORK', 'HOD approved PPW/2024/001', '192.168.1.101', '2024-01-11 06:00:00'),
(7, 6, 'APPROVE_PAPERWORK', 'Dean approved PPW/2024/001', '192.168.1.102', '2024-01-12 02:00:00'),
(8, 2, 'SUBMIT_PAPERWORK', 'Submitted PPW/2024/002', '192.168.1.100', '2024-01-15 05:30:00'),
(9, 5, 'REJECT_PAPERWORK', 'HOD returned PPW/2024/005', '192.168.1.101', '2024-01-09 03:20:00');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_permissions`
--

CREATE TABLE `tbl_permissions` (
  `permission_id` int(11) NOT NULL,
  `permission_name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_permissions`
--

INSERT INTO `tbl_permissions` (`permission_id`, `permission_name`, `description`, `created_at`) VALUES
(1, 'manage_users', 'Create, edit, and delete users', '2024-12-18 16:31:14'),
(2, 'manage_roles', 'Manage roles and permissions', '2024-12-18 16:31:14'),
(3, 'approve_submissions', 'Approve paperwork submissions', '2024-12-18 16:31:14'),
(4, 'create_submission', 'Create new paperwork submissions', '2024-12-18 16:31:14'),
(5, 'edit_submission', 'Edit existing submissions', '2024-12-18 16:31:14'),
(6, 'delete_submission', 'Delete submissions', '2024-12-18 16:31:14'),
(7, 'view_submissions', 'View paperwork submissions', '2024-12-18 16:31:14'),
(8, 'generate_reports', 'Generate system reports', '2024-12-18 16:31:14'),
(9, 'manage_departments', 'Manage department settings and view statistics', '2024-12-18 16:31:14'),
(10, 'view_analytics', 'Access analytics dashboard', '2024-12-18 16:31:14');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ppw`
--

CREATE TABLE `tbl_ppw` (
  `ppw_id` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `name` text NOT NULL,
  `session` varchar(255) NOT NULL,
  `project_name` varchar(255) NOT NULL,
  `project_date` date NOT NULL,
  `submission_time` timestamp NULL DEFAULT current_timestamp(),
  `status` varchar(50) DEFAULT NULL,
  `ref_number` varchar(50) NOT NULL,
  `ppw_type` varchar(50) NOT NULL,
  `document_path` varchar(255) DEFAULT NULL,
  `admin_note` text DEFAULT NULL,
  `current_stage` varchar(50) DEFAULT 'hod_review',
  `hod_approval` tinyint(1) DEFAULT NULL,
  `hod_note` text DEFAULT NULL,
  `hod_approval_date` datetime DEFAULT NULL,
  `dean_approval` varchar(255) DEFAULT NULL,
  `dean_note` text DEFAULT NULL,
  `dean_approval_date` datetime DEFAULT NULL,
  `user_email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ppw`
--

INSERT INTO `tbl_ppw` (`ppw_id`, `id`, `name`, `session`, `project_name`, `project_date`, `submission_time`, `status`, `ref_number`, `ppw_type`, `document_path`, `admin_note`, `current_stage`, `hod_approval`, `hod_note`, `hod_approval_date`, `dean_approval`, `dean_note`, `dean_approval_date`, `user_email`) VALUES
(3, 1, 'HON JUN YOON', '1', '1', '2024-12-11', '2024-12-11 03:19:21', NULL, '1', 'Project Proposal', '1733887161_Paper_v5.1_Improved.pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'joanchoo2201@hotmail.com'),
(4, 2, 'Matthew Hon', '12', '12', '2024-12-18', '2024-12-18 15:33:21', NULL, '2', 'Project Proposal', '1734536001_TOPIC_3_3.2.2_ACTIVITY[1].pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'honjunyoon@hotmail.com'),
(5, 3, 'John Staff', '2024/2025', 'AI-Based Learning Platform', '2024-01-15', '2024-01-15 01:30:00', '1', 'PPW/2024/001', 'Project Proposal', 'ai_learning_platform.pdf', NULL, 'approved', 1, 'Well-structured proposal', '2024-01-16 14:20:00', '1', 'Approved with commendation', '2024-01-17 10:15:00', 'john.staff@soc.edu.my'),
(6, 3, 'John Staff', '2024/2025', 'Cybersecurity Framework', '2024-01-20', '2024-01-20 03:45:00', NULL, 'PPW/2024/002', 'Research Paper', 'cybersecurity_framework.pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'john.staff@soc.edu.my'),
(7, 7, 'Mary Staff', '2024/2025', 'Blockchain Implementation', '2024-01-22', '2024-01-22 05:15:00', NULL, 'PPW/2024/003', 'Technical Report', 'blockchain_impl.pdf', NULL, 'dean_review', 1, 'Comprehensive analysis', '2024-01-23 09:30:00', NULL, NULL, NULL, 'mary.staff@soc.edu.my'),
(8, 7, 'Mary Staff', '2024/2025', 'IoT Security Protocol', '2024-01-25', '2024-01-25 07:20:00', '1', 'PPW/2024/004', 'Research Paper', 'iot_security.pdf', NULL, 'approved', 1, 'Innovative approach', '2024-01-26 11:45:00', '1', 'Excellent research', '2024-01-27 14:30:00', 'mary.staff@soc.edu.my'),
(9, 2, 'John Staff', '2024/2025', 'Machine Learning Framework', '2024-01-10', '2024-01-10 01:00:00', '1', 'PPW/2024/001', 'Project Proposal', 'ml_framework.pdf', NULL, 'approved', 1, 'Excellent proposal', '2024-01-11 14:00:00', '1', 'Well documented research', '2024-01-12 10:00:00', 'john.staff@soc.edu.my'),
(10, 2, 'John Staff', '2024/2025', 'Database Optimization Study', '2024-01-15', '2024-01-15 05:30:00', NULL, 'PPW/2024/002', 'Research Paper', 'db_optimization.pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'john.staff@soc.edu.my'),
(11, 5, 'Sarah Head', '2024/2025', 'Cybersecurity Best Practices', '2024-01-18', '2024-01-18 03:00:00', NULL, 'PPW/2024/003', 'Technical Report', 'security_practices.pdf', NULL, 'dean_review', 1, 'Comprehensive analysis', '2024-01-19 15:30:00', NULL, NULL, NULL, 'sarah.hod@soc.edu.my'),
(12, 4, 'Mary Staff', '2024/2025', 'AI Ethics Guidelines', '2024-01-20', '2024-01-20 02:15:00', NULL, 'PPW/2024/004', 'Project Proposal', 'ai_ethics.pdf', NULL, 'submitted', NULL, NULL, NULL, NULL, NULL, NULL, 'mary.staff@soc.edu.my'),
(13, 4, 'Mary Staff', '2024/2025', 'Cloud Computing Architecture', '2024-01-08', '2024-01-08 06:45:00', '0', 'PPW/2024/005', 'Technical Report', 'cloud_arch.pdf', NULL, 'rejected', 0, 'Needs more technical details', '2024-01-09 11:20:00', NULL, NULL, NULL, 'mary.staff@soc.edu.my'),
(14, 8, 'Alice Smith', '2024/2025', 'Software Engineering Project', '2024-01-10', '2024-01-09 17:00:00', '1', 'PPW/2024/006', 'Project Proposal', 'se_project.pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'alice.smith@soc.edu.my'),
(15, 9, 'Bob Johnson', '2024/2025', 'Cybersecurity Analysis', '2024-01-15', '2024-01-14 21:30:00', NULL, 'PPW/2024/007', 'Research Paper', 'cybersecurity_analysis.pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'bob.johnson@soc.edu.my'),
(16, 10, 'Charlie Brown', '2024/2025', 'Information Systems Study', '2024-01-20', '2024-01-19 23:20:00', '1', 'PPW/2024/008', 'Technical Report', 'is_study.pdf', NULL, 'hod_review', NULL, NULL, NULL, NULL, NULL, NULL, 'charlie.brown@soc.edu.my');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_roles`
--

CREATE TABLE `tbl_roles` (
  `role_id` int(11) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_roles`
--

INSERT INTO `tbl_roles` (`role_id`, `role_name`, `description`, `created_at`) VALUES
(1, 'super_admin', 'Full system access and control', '2024-12-18 16:31:14'),
(2, 'admin', 'System administration access', '2024-12-18 16:31:14'),
(3, 'dean', 'Dean level access for final approvals', '2024-12-18 16:31:14'),
(4, 'hod', 'Department head access for initial approvals', '2024-12-18 16:31:14'),
(5, 'staff', 'Basic staff access for submissions', '2024-12-18 16:31:14'),
(6, 'viewer', 'Read-only access to approved documents', '2024-12-18 16:31:14');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_role_permissions`
--

CREATE TABLE `tbl_role_permissions` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_role_permissions`
--

INSERT INTO `tbl_role_permissions` (`role_id`, `permission_id`, `created_at`) VALUES
(1, 1, '2024-12-19 01:39:51'),
(1, 2, '2024-12-19 01:39:51'),
(1, 3, '2024-12-19 01:39:51'),
(1, 4, '2024-12-19 01:39:51'),
(1, 5, '2024-12-19 01:39:51'),
(1, 6, '2024-12-19 01:39:51'),
(1, 7, '2024-12-19 01:39:51'),
(1, 8, '2024-12-19 01:39:51'),
(1, 9, '2024-12-19 01:39:51'),
(1, 10, '2024-12-19 01:39:51'),
(2, 1, '2024-12-18 16:43:58'),
(2, 3, '2024-12-18 16:43:58'),
(2, 7, '2024-12-18 16:43:58'),
(2, 8, '2024-12-18 16:43:58'),
(2, 9, '2024-12-18 16:43:58'),
(2, 10, '2024-12-18 16:43:58'),
(3, 3, '2024-12-18 16:43:58'),
(3, 7, '2024-12-18 16:43:58'),
(3, 8, '2024-12-18 16:43:58'),
(3, 9, '2024-12-22 02:46:05'),
(3, 10, '2024-12-18 16:43:58'),
(4, 3, '2024-12-18 16:43:58'),
(4, 7, '2024-12-18 16:43:58'),
(4, 8, '2024-12-18 16:43:58'),
(4, 9, '2024-12-22 02:46:05'),
(4, 10, '2024-12-22 02:46:05'),
(5, 4, '2024-12-18 16:43:58'),
(5, 5, '2024-12-18 16:43:58'),
(5, 6, '2024-12-18 16:43:58'),
(5, 7, '2024-12-18 16:43:58'),
(6, 7, '2024-12-18 16:43:58');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE `tbl_users` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `user_type` varchar(255) NOT NULL DEFAULT 'user',
  `register_time` datetime NOT NULL DEFAULT current_timestamp(),
  `active` tinyint(1) NOT NULL DEFAULT 1,
  `settings` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`settings`)),
  `department` varchar(100) DEFAULT NULL,
  `reporting_to` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `last_login` timestamp NULL DEFAULT NULL,
  `last_updated` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `reset_token` varchar(64) DEFAULT NULL,
  `reset_expires` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_users`
--

INSERT INTO `tbl_users` (`id`, `name`, `email`, `password`, `user_type`, `register_time`, `active`, `settings`, `department`, `reporting_to`, `phone`, `address`, `last_login`, `last_updated`, `reset_token`, `reset_expires`) VALUES
(1, 'HON JUN YOON', 'joanchoo2201@hotmail.com', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'admin', '2024-07-25 21:13:36', 1, '{\"theme\":\"light\",\"email_notifications\":true,\"browser_notifications\":false,\"compact_view\":false}', NULL, NULL, NULL, NULL, NULL, '2024-12-18 16:44:15', NULL, NULL),
(2, 'Matthew Hon', 'honjunyoon@hotmail.com', '$2y$10$rw8q9qHhIS4jQOEAhddhEOMHZ4bblaSLiRoip8T9SkBqMRsYq6Kuq', 'user', '2024-07-25 21:18:15', 1, '{\"theme\":\"light\",\"email_notifications\":true,\"browser_notifications\":false,\"compact_view\":false}', NULL, NULL, NULL, NULL, NULL, '2024-12-18 16:44:15', NULL, NULL),
(3, 'System Admin', 'admin@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'admin', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'IT Department', NULL, NULL, NULL, NULL, '2024-12-19 01:19:20', '59f32e61225bd1a525d10a2c6c6e6c8cdea6435ff25553724b1671eea9e6c948', '2024-12-19 03:19:20'),
(4, 'John Staff', 'john.staff@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'staff', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'Software Engineering', 4, NULL, NULL, NULL, '2024-12-19 02:06:18', NULL, NULL),
(5, 'Sarah Head', 'sarah.hod@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'hod', '2024-01-01 08:00:00', 1, '{\"theme\":\"dark\",\"email_notifications\":true}', 'Software Engineering', 5, NULL, NULL, NULL, '2024-12-19 02:06:30', NULL, NULL),
(6, 'David Dean', 'david.dean@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'dean', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'School of Computing', NULL, NULL, NULL, NULL, '2024-12-19 02:06:27', NULL, NULL),
(7, 'Mary Staff', 'mary.staff@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'staff', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'Cybersecurity', 3, NULL, NULL, NULL, '2024-12-19 02:06:23', NULL, NULL),
(8, 'Alice Smith', 'alice.smith@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'staff', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'Software Engineering', 5, NULL, NULL, NULL, '2024-12-18 18:06:23', NULL, NULL),
(9, 'Bob Johnson', 'bob.johnson@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'staff', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'Cybersecurity', 5, NULL, NULL, NULL, '2024-12-18 18:06:23', NULL, NULL),
(10, 'Charlie Brown', 'charlie.brown@soc.edu.my', '$2y$10$VEcdi7ITsNseyr9GCb7vKuKy5v3FOSmGo29dRM08lgmBALeT0UDgi', 'staff', '2024-01-01 08:00:00', 1, '{\"theme\":\"light\",\"email_notifications\":true}', 'Information Systems', 5, NULL, NULL, NULL, '2024-12-18 18:06:23', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user_roles`
--

CREATE TABLE `tbl_user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `assigned_by` int(11) DEFAULT NULL,
  `assigned_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `last_modified_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `last_modified_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user_roles`
--

INSERT INTO `tbl_user_roles` (`user_id`, `role_id`, `assigned_by`, `assigned_at`, `last_modified_at`, `last_modified_by`) VALUES
(1, 1, 1, '2024-12-19 01:35:41', '2024-12-19 01:35:41', NULL),
(2, 5, 1, '2024-12-19 01:35:41', '2024-12-19 01:35:41', NULL),
(3, 1, 1, '2024-01-01 00:00:00', '2024-12-18 16:52:20', NULL),
(4, 5, 1, '2024-01-01 00:00:00', '2024-12-18 16:52:20', NULL),
(5, 4, 1, '2024-01-01 00:00:00', '2024-12-18 16:52:20', NULL),
(6, 3, 1, '2024-01-01 00:00:00', '2024-12-18 16:52:20', NULL),
(7, 5, 1, '2024-01-01 00:00:00', '2024-12-18 16:52:20', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_audit_log`
--
ALTER TABLE `tbl_audit_log`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `tbl_permissions`
--
ALTER TABLE `tbl_permissions`
  ADD PRIMARY KEY (`permission_id`),
  ADD UNIQUE KEY `permission_name` (`permission_name`),
  ADD KEY `idx_permission_name` (`permission_name`);

--
-- Indexes for table `tbl_ppw`
--
ALTER TABLE `tbl_ppw`
  ADD PRIMARY KEY (`ppw_id`),
  ADD KEY `id` (`id`),
  ADD KEY `idx_user_email` (`user_email`);

--
-- Indexes for table `tbl_roles`
--
ALTER TABLE `tbl_roles`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `role_name` (`role_name`),
  ADD KEY `idx_role_name` (`role_name`);

--
-- Indexes for table `tbl_role_permissions`
--
ALTER TABLE `tbl_role_permissions`
  ADD PRIMARY KEY (`role_id`,`permission_id`),
  ADD KEY `permission_id` (`permission_id`),
  ADD KEY `idx_role_permission` (`role_id`,`permission_id`),
  ADD KEY `idx_role_permission_combined` (`role_id`,`permission_id`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_email` (`email`);

--
-- Indexes for table `tbl_user_roles`
--
ALTER TABLE `tbl_user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `role_id` (`role_id`),
  ADD KEY `idx_user_role` (`user_id`,`role_id`),
  ADD KEY `idx_assigned` (`assigned_by`,`assigned_at`),
  ADD KEY `last_modified_by` (`last_modified_by`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_audit_log`
--
ALTER TABLE `tbl_audit_log`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tbl_permissions`
--
ALTER TABLE `tbl_permissions`
  MODIFY `permission_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tbl_ppw`
--
ALTER TABLE `tbl_ppw`
  MODIFY `ppw_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `tbl_roles`
--
ALTER TABLE `tbl_roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_users`
--
ALTER TABLE `tbl_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_audit_log`
--
ALTER TABLE `tbl_audit_log`
  ADD CONSTRAINT `fk_audit_user` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`);

--
-- Constraints for table `tbl_ppw`
--
ALTER TABLE `tbl_ppw`
  ADD CONSTRAINT `fk_user_email` FOREIGN KEY (`user_email`) REFERENCES `tbl_users` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_ppw_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tbl_users` (`id`);

--
-- Constraints for table `tbl_role_permissions`
--
ALTER TABLE `tbl_role_permissions`
  ADD CONSTRAINT `tbl_role_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `tbl_roles` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_role_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `tbl_permissions` (`permission_id`) ON DELETE CASCADE;

--
-- Constraints for table `tbl_user_roles`
--
ALTER TABLE `tbl_user_roles`
  ADD CONSTRAINT `tbl_user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `tbl_roles` (`role_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `tbl_user_roles_ibfk_3` FOREIGN KEY (`assigned_by`) REFERENCES `tbl_users` (`id`),
  ADD CONSTRAINT `tbl_user_roles_ibfk_4` FOREIGN KEY (`last_modified_by`) REFERENCES `tbl_users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
