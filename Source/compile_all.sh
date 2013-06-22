#!/bin/bash
#alias java=/usr/lib/jvm/jdk1.7.0/bin/java
#alias javac=/usr/lib/jvm/jdk1.7.0/bin/javac
#alias jar=/usr/lib/jvm/jdk1.7.0/bin/jar

build_module() {
  echo "test $SRC_FILES"
  echo "compiling $MODULE .."
  cd $MODULE/src
  rm -R ../dist
  mkdir ../dist
  rm -R ../build
  mkdir ../build
  mkdir ../build/classes
  javac -classpath ".:../lib/*" -d ../build/classes $SRC_FILES
  cp ../../$MANIFEST ../build/classes/manifest.mf
  cd ../build/classes
  jar -cvfm ../../dist/${MODULE}.jar manifest.mf $CLASS_FILES
  cd ../../src
  jar -uvfm ../dist/${MODULE}.jar ../build/classes/manifest.mf $OTHER_FILES
  cd ..
  cp -r lib dist/
  cd ..
}

MANIFEST=manifest.mf
MODULE="SemanticTagger"
SRC_FILES="./ir/ac/iust/nlp/semantictagger/*.java ./ir/ac/iust/nlp/semantictagger/enums/*.java ./ir/ac/iust/nlp/semantictagger/utility/*.java"
CLASS_FILES="./ir/ac/iust/nlp/semantictagger/*.class ./ir/ac/iust/nlp/semantictagger/enums/*.class ./ir/ac/iust/nlp/semantictagger/utility/*.class"
OTHER_FILES="./ir/ac/iust/nlp/semantictagger/*.form ./ir/ac/iust/nlp/semantictagger/*.properties ./ir/ac/iust/nlp/semantictagger/*.png"
build_module

cd SemanticTagger
cp -r data dist/
cp -r dict dist/
cp -r lib dist/
cp -r sensesRelations0.xml dist/
cp -r synsets0.xml dist/
cp -r synsetsRelation0.xml dist/
cp -r words0.xml dist/
cp -r LICENSE.TXT dist/
cp -r README.TXT dist/