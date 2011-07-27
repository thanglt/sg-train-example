#!/usr/bin/perl 
#===============================================================================
#
#         FILE:  mysqlbackup.pl
#
#        USAGE:  ./mysqlbackup.pl  
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
use DBI;

#use List::Util qw(first max maxstr min minstr reduce shuffle sum);

my $BACKUP_BASE_FOLDER = "/home/huangzhen";
my $excludes = "_schema|mephisto";

#my $excludes = qw(mephisto);

$BACKUP_BASE_FOLDER = "c:/home/huangzhen" if $^O eq "MSWin32";

make_path($BACKUP_BASE_FOLDER) unless -e $BACKUP_BASE_FOLDER;

my $dt = DateTime->now;

my $today_folder = &getFullPath($dt->ymd);

make_path($today_folder);

my $dbh = DBI->connect('dbi:mysql:information_schema', "root", "197365", { 'RaiseError' => 1 } );
my $databases = $dbh->selectcol_arrayref('show databases');

foreach (@{$databases}){
	my $dbfname = File::Spec->join($today_folder,$_ . ".sql");
	if(!/$excludes/i){
		qx/mysqldump -uroot -p197365 $_ > $dbfname/;
	}
}

my $dt_7 = $dt->subtract(days=> 7);
my $folder_7 = &getFullPath($dt_7->ymd);
remove_tree($folder_7);

sub getFullPath(){
	my ($refile) = @_;
	return File::Spec->join($BACKUP_BASE_FOLDER,$refile);
}

