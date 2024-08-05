-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 22, 2024 at 05:38 PM
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
-- Database: `contactdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `coursepres`
--

CREATE TABLE `coursepres` (
  `coursePresId` int(11) NOT NULL,
  `courseId` int(11) NOT NULL,
  `insCode` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `coursepres`
--

INSERT INTO `coursepres` (`coursePresId`, `courseId`, `insCode`) VALUES
(8522, 2003, 1005),
(8555, 2001, 1001),
(9000, 2006, 1008),
(9001, 2004, 1006),
(9999, 2007, 1002);

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `courseId` int(11) NOT NULL,
  `title` varchar(30) NOT NULL,
  `unitNumber` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`courseId`, `title`, `unitNumber`) VALUES
(2001, 'Analaysis and Design', 3),
(2002, 'Artificial Inteligence', 3),
(2003, 'Logical Circuts', 3),
(2004, 'Dijital Systems', 3),
(2005, 'Basics of Programming', 3),
(2006, 'Advanced Programming', 4),
(2007, 'Databases', 3),
(2008, 'Network', 3),
(2009, 'Operating Systems', 3),
(2010, 'Hardware', 3);

-- --------------------------------------------------------

--
-- Table structure for table `coursesel`
--

CREATE TABLE `coursesel` (
  `courseSelId` int(11) NOT NULL,
  `stCode` int(11) NOT NULL,
  `coursePresId` int(11) NOT NULL,
  `grade` decimal(4,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `coursesel`
--

INSERT INTO `coursesel` (`courseSelId`, `stCode`, `coursePresId`, `grade`) VALUES
(1, 3998026, 9999, 19.25),
(3, 3998026, 9001, 0.00);

-- --------------------------------------------------------

--
-- Table structure for table `instructors`
--

CREATE TABLE `instructors` (
  `insCode` int(11) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `gender` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `instructors`
--

INSERT INTO `instructors` (`insCode`, `firstName`, `lastName`, `gender`) VALUES
(1001, 'Elnaz', 'Zafarani Moatar', 1),
(1002, 'Amin', 'Khodayi', 0),
(1003, 'Shiva', 'Taghipour', 1),
(1004, 'Shahram', 'Babayi', 0),
(1005, 'Nahide', 'Derakhshanfard', 1),
(1006, 'Ahad', 'Habibi', 0),
(1007, 'Mitra', 'Sarhangzadeh', 1),
(1008, 'Seyed Sajad', 'Pirahesh', 0),
(1009, 'Mortaza', 'Abbaszade', 0),
(1010, 'Nasim', 'Argha', 1),
(1011, 'Saied', 'Mohammadi', 0),
(1012, 'Reza', 'Mohammadnezah bisharaf ziyad', 0);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `stCode` int(11) NOT NULL,
  `firstName` varchar(30) NOT NULL,
  `lastName` varchar(30) NOT NULL,
  `gender` tinyint(4) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`stCode`, `firstName`, `lastName`, `gender`) VALUES
(3991024, 'Reza', 'Sabzi Mehtarloo', 0),
(3998025, 'Sina', 'Balar', 0),
(3998026, 'Milad', 'Agahi', 0),
(3998030, 'Draya', 'Tabesh', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `userid` int(11) NOT NULL,
  `username` varchar(30) NOT NULL,
  `userpass` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `fullname` varchar(50) NOT NULL,
  `stat` tinyint(4) NOT NULL DEFAULT 0,
  `userType` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`userid`, `username`, `userpass`, `fullname`, `stat`, `userType`) VALUES
(1, 'admin', '123456', 'System Administrator', 1, 2),
(2, 'rezasabzi', '123456', 'Reza Sabzi Mehtarloo', 1, 1),
(3, 'miladag', '123456', 'milad agahi', 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `coursepres`
--
ALTER TABLE `coursepres`
  ADD PRIMARY KEY (`coursePresId`),
  ADD KEY `FK_coursePres_course_courseId` (`courseId`),
  ADD KEY `FK_coursePres_instructor_insCode` (`insCode`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`courseId`);

--
-- Indexes for table `coursesel`
--
ALTER TABLE `coursesel`
  ADD PRIMARY KEY (`courseSelId`),
  ADD KEY `FK_coursesel_courseprs_coursePresId` (`coursePresId`),
  ADD KEY `FK_coursesel_student_stCode` (`stCode`);

--
-- Indexes for table `instructors`
--
ALTER TABLE `instructors`
  ADD PRIMARY KEY (`insCode`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`stCode`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `coursepres`
--
ALTER TABLE `coursepres`
  MODIFY `coursePresId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=282829;

--
-- AUTO_INCREMENT for table `coursesel`
--
ALTER TABLE `coursesel`
  MODIFY `courseSelId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `coursepres`
--
ALTER TABLE `coursepres`
  ADD CONSTRAINT `FK_coursePres_course_courseId` FOREIGN KEY (`courseId`) REFERENCES `courses` (`courseId`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_coursePres_instructor_insCode` FOREIGN KEY (`insCode`) REFERENCES `instructors` (`insCode`) ON UPDATE CASCADE;

--
-- Constraints for table `coursesel`
--
ALTER TABLE `coursesel`
  ADD CONSTRAINT `FK_coursesel_courseprs_coursePresId` FOREIGN KEY (`coursePresId`) REFERENCES `coursepres` (`coursePresId`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_coursesel_student_stCode` FOREIGN KEY (`stCode`) REFERENCES `students` (`stCode`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
