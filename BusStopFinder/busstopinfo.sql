-- phpMyAdmin SQL Dump
-- version 3.3.9
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Apr 29, 2012 at 07:47 PM
-- Server version: 5.5.8
-- PHP Version: 5.3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `busstopinfo`
--

-- --------------------------------------------------------

--
-- Table structure for table `busstoppos`
--

CREATE TABLE IF NOT EXISTS `busstoppos` (
  `id` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `x_pos` varchar(10) NOT NULL,
  `y_pos` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `busstoppos`
--

INSERT INTO `busstoppos` (`id`, `name`, `x_pos`, `y_pos`) VALUES
(1, ' bergasa station', ' 	56.18234', ' 15.600929'),
(2, ' valhallavagen', ' 56.182003', ' 15.59918'),
(3, ' sunnavagen', ' 56.180767', ' 15.599964'),
(4, ' krutviksvagen', ' 56.18126', ' 15.60145'),
(5, ' lasarettsvagen', ' 56.18223', ' 15.605156'),
(6, 'gamla infartsvagen', ' 56.186004', '15.604792'),
(7, ' campus grasvik', ' 56.180636', '15.592936'),
(8, ' polhemsgatan', ' 56.17676', '15.590415'),
(9, ' Utridarevagen', '56.178659', '15.594707'),
(10, 'Lasarettsvagen', '56.18223', '15.605178'),
(11, 'Hastovagen', '56.178731', '15.612345'),
(12, 'Blockhusvagen', '56.18106', '15.618138'),
(13, 'Herrgardsvagen', '56.181275', '15.621679'),
(14, 'Coldinuvagen', '56.181943', '15.625155'),
(15, 'Rombvagen', '56.164169', '15.638244'),
(16, 'Verkovagen', '56.166929', '15.633996'),
(17, 'Toras Vag', '56.170883', '15.642514'),
(18, 'Sodra Backen', '56.173045', '15.644574'),
(20, 'Hananasbacken', '56.176079', '15.651033'),
(21, 'Ringovagen', '56.180092', '15.65599'),
(22, 'Bengtsavagen', '56.187759', '15.656097'),
(23, 'Bengtsavagen', '56.188786', '15.662749'),
(24, 'Bengtsavagen', '56.188989', '15.664508'),
(25, 'Knosovagen', '56.191424', '15.663929');
