-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 06, 2017 at 08:19 AM
-- Server version: 10.1.19-MariaDB
-- PHP Version: 5.6.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `ID` int(11) NOT NULL,
  `FirstName` varchar(255) DEFAULT NULL,
  `LastName` varchar(255) DEFAULT NULL,
  `Age` varchar(11) DEFAULT NULL,
  `Qualification` varchar(255) DEFAULT NULL,
  `Status` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`ID`, `FirstName`, `LastName`, `Age`, `Qualification`, `Status`) VALUES
(9, 'tushar', 'moraye', '20', 'cs', 1),
(10, 'tushar', 'moraye', '20', 'cs', 1),
(11, 'tushar', 'moraye', '20', 'cs', 1),
(12, 'tushar', 'moraye', '20', 'cs', 1),
(13, 'tushar', 'moraye', '20', 'cs', 1),
(14, 'tushar', 'moraye', '20', 'cs', 1),
(15, 'tushar', 'moraye', '20', 'cs', 1),
(16, 'tushar', 'moraye', '20', 'cs', 1),
(17, 'tushar', 'moraye', '20', 'cs', 1),
(18, 'tushar', 'moraye', '20', 'cs', 1),
(19, 'tushar', 'moraye', '20', 'cs', 1),
(20, 'tushar', 'moraye', '20', 'cs', 1),
(21, 'rupesh', 'dhuri', '20', 'civil', 1),
(22, 'ramesh', 'dhuri ', '17', 'br', 1),
(23, 'hey', 'moraye', '45', 'cd', 1),
(24, 'new ', 'new', '50', 'new', 1),
(25, 'ttty', 'ttty', '4', 'ttty', 1),
(26, 'ttty', 'ttty', '4', 'ttty', 1),
(27, 'aaaa', 'aaa', '43', 'tqq', 1),
(28, 'aaaa', 'aaa', '43', 'tqq', 1),
(29, 'asf', 'jf', '3', 'ey', 1),
(30, 'ttty', 'ttty', '4', 'ttty', 1),
(31, 'tusjjs', 'yhsbs', '17', 'eu', 1),
(32, 'a', 'b', '1', 'd', 1),
(33, 'p', 'q', '17', 's', 1),
(34, 'l', 'm', '7', 'o', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Id` int(11) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `syncsts` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Id`, `Name`, `syncsts`) VALUES
(1, 'tushar', 0),
(2, 'tushar', 0),
(3, 'rupesh', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `ID` (`ID`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
