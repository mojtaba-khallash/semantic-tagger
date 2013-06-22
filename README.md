In the name of Allah

SemanticTagger version 1.0
===================
      19 January 2013

This is the README for the *"SemanticTagger" package* which helps adding 
semantic feature to dependency parsing. This package has been developed by 
[Mojtaba Khallash] (mailto: mkhallash@gmail.com) from _Iran University of Science and 
Technology (IUST)_.

The home page for the project is:
  http://nlp.iust.ac.ir
	
If you want to use this software for research, please refer to this web address 
in your papers.

The package can be used freely for non-commercial research and educational 
purposes. It comes with no  warranty, but we welcome all comments, bug reports, 
and suggestions for improvements.

Table of contents
------------------

1. Compiling
2. Example of usage
3. Running the package
4. References

1. Compiling
----------------

Requirements:
* Version 1.7 or later of the [Java 2 SDK] (http://java.sun.com)
You must add java binary file to system path. <br/>In linux, your
can open `~/.bashrc` file and append this line:
`PATH=$PATH:/<address-of-bin-folder-of-JRE>`

To compile the code, first decompress the package:

in linux:
> tar -xvzf SemanticTagger.tgz<br/>
> cd SemanticTagger<br/>
> sh compile_all.sh

in windows:
> decompress the SemanticTagger.zip<br/>
> compile.bat

You can open the all projects in NetBeans 7.1 (or maybe later) too.

2. Example of Usage
---------------------

Due to restricted pulish for farsnet we couldn't place the [FarsNet] (http://nlp.sbu.ac.ir/site/farsnet/) files
to this package. For running the package you must download related files and
put them manually in right places.
After installing FarsNet, It creates 2 folders, dict and lib. You must copy:
<ol>
	<li><code>dict</code> folder in FarsNet installation directory to SemanticTagger/dict/</li>
	<li><code>FarsNetBrowser.jar</code> to SemanticTagger/lib/</li>
	<li><code>sensesRelations0.xml</code> to SemanticTagger/ (root)</li>
	<li><code>synsetsRelation0.xml</code> to SemanticTagger/ (root)</li>
	<li><code>words0.xml</code> to SemanticTagger/ (root)</li>
	<li><code>synsets0.xml</code> to SemanticTagger/ (root)</li>
</ol>

3. Running the package
-------------------------

This package run in gui mode. simple double click on jar file or run the 
following command:

> java -jar TreebankTransform.jar

3a. Global Transforms
----------------------

This package run in two mode: <br/>

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
	intput CoNLL file that want to add word cluster id to FEATS column.

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

4. References
------------
[1]	M. Shamsfard, et al., "Semi Automatic Development Of FarsNet: The 
Persian Wordnet," in Proceedings of 5th Global WordNet Conference (GWA2010), 
Mumbai, India, 2010.