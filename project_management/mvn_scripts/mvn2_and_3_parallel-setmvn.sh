#!/bin/bash

# source: http://blog.organicelement.com/blog/managing-multiple-maven-versions/

MAVEN_PARENT=/parent/containing/apache-maven-x.y.z/folders

function defaultmvn {
	local mvndir=$MAVEN_PARENT
	local ver=${1?Usage: defaultmvn <version>}
	local mvnprefix=apache-maven-
	
	[ -z "$2" ] || error="Too many arguments"
	[ -d $mvndir/$mvnprefix$ver ] || local error="Unknown Maven version: $ver"
	[ "$(readlink $mvndir/apache-maven)" != "$mvndir/$mvnprefix$ver" ] || local error="Default Maven already set to $ver"
	
	if [ -n "$error" ]; then
	echo $error
	return 1
	fi
	
	echo "Setting default Maven to $ver ... "
	
	if [ "$(/usr/bin/id -u)" != "0" ]; then
	SUDO=sudo
	fi
	
	$SUDO /bin/rm $mvndir/apache-maven
	$SUDO /bin/ln -s $mvndir/$mvnprefix$ver $mvndir/apache-maven
	
	echo Done.
}

function setmvn {
	local mvndir=$MAVEN_PARENT
	local ver=${1?Usage: setmvn <version>}
	local mvnprefix=apache-maven-
	
	[ -d $mvndir/$mvnprefix$ver ] || {
		echo Unknown Maven version: $ver
		return 1
	}

	echo "Setting current Maven version to $ver ..."
	
	export M2_HOME=$mvndir/$mvnprefix$ver
	PATH=$(echo $PATH | tr ':' '\n' | grep -v $mvndir/$mvnprefix | tr '\n' ':')
	export PATH=$M2_HOME/bin:$PATH
	
	mvn -v
}

function _setmvn_completion (){
	COMPREPLY=()
	
	local mvndir=$MAVEN_PARENT
	local cur=${COMP_WORDS[COMP_CWORD]//\\\\/}
	local options=$(cd $mvndir; ls -d -1 apache-maven-* | awk '{match($0,"apache-maven-.*"); print substr($0,14,RLENGTH-13)}' | tr '\n' ' ')
	
	COMPREPLY=($(compgen -W "${options}" ${cur}))
}

complete -F _setmvn_completion -o filenames setmvn
complete -F _setmvn_completion -o filenames defaultmvn
