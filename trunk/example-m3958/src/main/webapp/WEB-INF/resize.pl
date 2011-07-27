#!/usr/bin/perl 
#===============================================================================
#
#         FILE:  resize.pl
#
#        USAGE:  ./resize.pl  
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
#      CREATED:  2011-1-17 9:30:12
#     REVISION:  ---
#===============================================================================

use strict;
use warnings;
use File::Spec;

use Image::Magick;

my @IMG_SIZES = qw/800x600> 128x128> 48x48>/;

#48x48,64x64,96x96,128x128,144x144,160x160,192x192
#@ARGV = qw/E:\\abc 2011\\01\\06\\3f9280a0-9597-4244-ac0b-0cdaa2550c2d.jpg 35x35/ unless(@ARGV == 2 || @ARGV == 3);

if(@ARGV >= 3){
#	$_ = $ARGV[2];
	@IMG_SIZES = ();
	my @ars = @ARGV[2..@ARGV-1]; 
	foreach(@ars){
		my ($w,$h) = split /x/;
		die unless ($w < 1200 && $h < 1200);
		push(@IMG_SIZES,$_ . ">");
	}
}
print @IMG_SIZES;


my ($basedir,$infn) = @ARGV;

my $baspath = File::Spec->join($basedir,$infn);
print $baspath;

my $image = Image::Magick->new;
$image->Read($baspath);

#my ($width,$height) = $image->Get('columns','rows');


foreach (@IMG_SIZES){
	$image->Resize($_);
	$image->Write(&getfn($baspath,$_));
}

undef $image;

sub getfn(){
	my ($fn,$size) = @_;
	my ($bfn,$ext) = $fn =~ /(.*)(\.[^.]+)$/;
	my ($tsize) = $size =~ /^([0-9x]+)/;
	return	$bfn.'.'.$tsize.$ext;
}

