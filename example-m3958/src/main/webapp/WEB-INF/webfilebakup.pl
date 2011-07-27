#!/usr/bin/perl 
#===============================================================================
#
#         FILE:  webfilebakup.pl
#
#        USAGE:  ./webfilebakup.pl  
#
#  DESCRIPTION:  
#
#      OPTIONS:  ---
# REQUIREMENTS:  ---
#         BUGS:  ---
#        NOTES:  ---
#       AUTHOR:  jianglibo@gmail.com, 
#      COMPANY:  
#      VERSION:  1.0
#      CREATED:  2011-1-19 18:44:54
#     REVISION:  ---
#===============================================================================

use strict;
use warnings;

use DateTime;
use File::Spec;
use File::Path qw(make_path remove_tree);

#use List::Util qw(first max maxstr min minstr reduce shuffle sum);

my $BACKUP_SOURCE_FOLDER = "/home/huangzhen/www";
my $BACKUP_DST_FOLDER = "/home/huangzhen/wwwbak/";
my $DST_FILE_PREFIX = "sitebak";

$BACKUP_SOURCE_FOLDER = "c:/home/huangzhen/www" if $^O eq "MSWin32";
$BACKUP_DST_FOLDER = "c:/home/huangzhen/wwwbak/" if $^O eq "MSWin32";

make_path($BACKUP_DST_FOLDER) unless -e $BACKUP_DST_FOLDER;

my $dt = DateTime->now;

my $cmd = "/usr/bin/tar cvzf ${BACKUP_DST_FOLDER}${DST_FILE_PREFIX}." . $dt->ymd() . ".tgz ${BACKUP_SOURCE_FOLDER}";
print $cmd;
qx!$cmd!;


