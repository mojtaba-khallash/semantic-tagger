In the name of Allah

------------------------------
SemanticTagger version 1.0
      19 January 2013
------------------------------

This is the README for the "SemanticTagger" package which helps adding 
semantic feature to dependency parsing. This package has been developed by 
Mojtaba Khallash (mkhallash@gmail.com) from Iran University of Science and 
Technology (IUST).

The home page for the project is:
	http://nlp.iust.ac.ir

The code is available on Github at:
	https://github.com/mojtaba-khallash/semantic-tagger

If you want to use this software for research, please refer to this web address 
in your papers.

-------------------------

The package can be used freely for non-commercial research and educational 
purposes. It comes with no  warranty, but we welcome all comments, bug reports, 
and suggestions for improvements.

------------------
Table of contents
------------------

1. Compiling

2. Example of usage

3. Running the package

4. References

----------------
1. Compiling
----------------

Requirements:
* Version 1.7 or later of the Java 2 SDK (http://java.sun.com)
  You must add java binary file to system path. In linux, your
  can open ~/.bashrc file and append this line:
    PATH=$PATH:/<address-of-bin-folder-of-JRE>

To compile the code, first decompress the package:

in linux:
$ tar -xvzf SemanticTagger.tgz
$ cd SemanticTagger
$ sh compile_all.sh

in windows:
- decompress the SemanticTagger.zip
- compile.bat

You can open the all projects in NetBeans 7.1 (or maybe later) too.

---------------------
2. Example of Usage
---------------------

Due to restricted pulish for farsnet we couldn't place the FarsNet files
to this package. For running the package you must download related files and
put them manually in right places.
After installing FarsNet, It creates 2 folders, dict and lib. You must copy:
1- dict folder in FarsNet installation directory to SemanticTagger/dict/
2- FarsNetBrowser.jar to SemanticTagger/lib/
3- sensesRelations0.xml to SemanticTagger/ (root)
4- synsetsRelation0.xml to SemanticTagger/ (root)
5- words0.xml to SemanticTagger/ (root)
6- synsets0.xml to SemanticTagger/ (root)

-------------------------
3. Running the package
-------------------------

This package run in two mode: 

* gui [default mode]
In this mode you can add semantic tags by hand. For each word in treebank, 
you can search in FarsNet and show list of synsets and you must
determine which of them is correct. Simply double click on jar file or 
run the following command:

> java -jar SemanticTagger.jar

* command-line
In this mode you can set input file to automatically set semantic tags.
In order to running package in command-line mode you must be set -v flag 
(visible) to 0:

> java -jar SemanticTagger.jar -v 0 -i <input-file> -o <output-file> \
  -t <type-of-semantic-tag (SS|SF)>

-i <input conll file>
	intput CoNLL file that want to add semantic tag to FEATS column.

-o <output conll file>
	name of output CoNLL file after adding feature

-t <type-of-semantic-tag (SS|SF)>
	Type of semantic tag that want to add to FEATS column.
		SS -> Synset id from FarsNet
		SF -> Semantic file from WordNet
		
For example:

> java -jar SemanticTagger.jar -v 0 -i input.conll -o output.conll -t SF

Requirements:
** "FarsNetBrowser.jar" (http://nlp.sbu.ac.ir/site/farsnet/).

------------
References
------------
[1]	M. Shamsfard, et al., "Semi Automatic Development Of FarsNet: The 
    Persian Wordnet", in Proceedings of 5th Global WordNet Conference (GWA2010), 
    Mumbai, India, 2010.