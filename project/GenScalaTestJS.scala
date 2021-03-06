/*
* Copyright 2001-2015 Artima, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import io.Source
import java.io.{File, FileWriter, BufferedWriter}

object GenScalaTestJS {

  private def uncommentJsExport(line: String): String =
    if (line.trim.startsWith("//SCALATESTJS-ONLY "))
      line.substring(line.indexOf("//SCALATESTJS-ONLY ") + 19)
    else
      line

  private def transformLine(line: String): String =
    uncommentJsExport(line)

  private def copyFile(sourceFile: File, destFile: File): File = {
    val destWriter = new BufferedWriter(new FileWriter(destFile))
    try {
      val lines = Source.fromFile(sourceFile).getLines.toList
      var skipMode = false
      for (line <- lines) {
        if (line.trim == "// SKIP-SCALATESTJS-START")
          skipMode = true
        else if (line.trim == "// SKIP-SCALATESTJS-END")
          skipMode = false
        else if (!skipMode) {
          destWriter.write(transformLine(line))
          destWriter.newLine()
        }
      }
      destFile
    }
    finally {
      destWriter.flush()
      destWriter.close()
      println("Copied " + destFile.getAbsolutePath)
    }
  }

  def copyDir(sourceDirName: String, packageDirName: String, files: List[String], targetDir: File): Seq[File] = {
    val packageDir = new File(targetDir, packageDirName)
    packageDir.mkdirs()
    val sourceDir = new File(sourceDirName)
    files.map { sourceFileName =>
      val sourceFile = new File(sourceDir, sourceFileName)
      val destFile = new File(packageDir, sourceFile.getName)
      copyFile(sourceFile, destFile)
    }
  }

  def genJava(targetDir: File, version: String, scalaVersion: String): Seq[File] = {
    copyDir("scalatest/src/main/java/org/scalatest", "org/scalatest",
            List(
              "Finders.java",
              "TagAnnotation.java",
              "WrapWith.java",
              "DoNotDiscover.java"
            ), targetDir)
  }

  def genScala(targetDir: File, version: String, scalaVersion: String): Seq[File] = {

    copyDir("scalatest/src/main/scala/org/scalatest", "org/scalatest",
            List(
              "Suite.scala",
              "OutcomeOf.scala",
              "Assertions.scala",
              "Outcome.scala",
              "TestData.scala",
              "ConfigMap.scala",
              "Reporter.scala",
              "DispatchReporter.scala",
              "CatchReporter.scala",
              "ResourcefulReporter.scala",
              "Tracker.scala",
              "Filter.scala",
              "DynaTags.scala",
              "Status.scala",
              "Args.scala",
              "Stopper.scala",
              "Distributor.scala",
              "DistributedTestSorter.scala",
              "DistributedSuiteSorter.scala",
              "Informer.scala",
              "EncodedOrdering.scala",
              "ConcurrentInformer.scala",
              "Documenter.scala",
              "SuiteHelpers.scala",
              "PendingNothing.scala",
              "AssertionsMacro.scala",
              "CompileMacro.scala",
              "AppendedClues.scala",
              "Notifier.scala",
              "Alerter.scala",
              "SlowpokeDetector.scala",
              "Slowpoke.scala",
              "RunningTest.scala",
              "ParallelTestExecution.scala",
              "OneInstancePerTest.scala",
              "SuiteMixin.scala",
              "Engine.scala",
              "Tag.scala",
              "FunSuiteRegistration.scala",
              "FunSuiteLike.scala",
              "FunSuite.scala",
              "TestRegistration.scala",
              "Informing.scala",
              "Notifying.scala",
              "Alerting.scala",
              "Documenting.scala",
              "Transformer.scala",
              "DeferredAbortedSuite.scala",
              "Suites.scala",
              "FunSpecRegistration.scala",
              "FunSpecLike.scala",
              "FunSpec.scala",
              "UnquotedString.scala",
              "FlatSpecRegistration.scala",
              "FlatSpecLike.scala",
              "FlatSpec.scala",
              "WordSpecRegistration.scala",
              "WordSpecLike.scala",
              "WordSpec.scala",
              "FreeSpecRegistration.scala",
              "FreeSpecLike.scala",
              "FreeSpec.scala",
              "PropSpecRegistration.scala",
              "PropSpecLike.scala",
              "PropSpec.scala",
              "FeatureSpecRegistration.scala",
              "FeatureSpecLike.scala",
              "FeatureSpec.scala",
              "MatchersHelper.scala",
              "Matchers.scala",
              "Inspectors.scala",
              "OptionValues.scala",
              "Inside.scala",
              "NonImplicitAssertions.scala",
              "AsyncOutcome.scala",
              "ClassicTests.scala",
              "package.scala",
              "LoneElement.scala"
            ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/fixture", "org/scalatest/fixture",
            List(
              "Suite.scala",
              "TestDataFixture.scala",
              "TestRegistration.scala",
              "Transformer.scala",
              "UnitFixture.scala",
              "NoArg.scala",
              "NoArgTestWrapper.scala",
              "FixtureNodeFamily.scala",
              "FunSuiteRegistration.scala",
              "FunSuiteLike.scala",
              "FunSuite.scala",
              "FlatSpecRegistration.scala",
              "FlatSpecLike.scala",
              "FlatSpec.scala",
              "FunSpecRegistration.scala",
              "FunSpecLike.scala",
              "FunSpec.scala",
              "WordSpecRegistration.scala",
              "WordSpecLike.scala",
              "WordSpec.scala",
              "FreeSpecRegistration.scala",
              "FreeSpecLike.scala",
              "FreeSpec.scala",
              "PropSpecRegistration.scala",
              "PropSpecLike.scala",
              "PropSpec.scala",
              "FeatureSpecRegistration.scala",
              "FeatureSpecLike.scala",
              "FeatureSpec.scala",
              "ClassicTests.scala"
            ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/events", "org/scalatest/events",
            List(
              "Event.scala",
              "Ordinal.scala",
              "Formatter.scala",
              "Location.scala",
              "Summary.scala",
              "NameInfo.scala"
            ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/matchers", "org/scalatest/matchers",
            List(
              "MatchResult.scala",
              "AMatcher.scala",
              "AnMatcher.scala",
              "BeMatcher.scala",
              "BePropertyMatchResult.scala",
              "BePropertyMatcher.scala",
              "HavePropertyMatchResult.scala",
              "HavePropertyMatcher.scala",
              "LazyArg.scala",
              "LazyMessage.scala",
              "Matcher.scala",
              "TypeMatcherMacro.scala",
              "MatchPatternMacro.scala",
              "MatcherProducers.scala",
              "MatchFailed.scala",
              "MatchPatternHelper.scala",
              "MatchSucceeded.scala",
              "TypeMatcherHelper.scala"
            ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/tools", "org/scalatest/tools",
      List(
        "SuiteDiscoveryHelper.scala",
        "StringReporter.scala",
        "SuiteRunner.scala",
        "Fragment.scala",
        "ParsedArgs.scala",
        "ReporterConfiguration.scala",
        "AnsiColor.scala",
        "ReporterConfigParam.scala",
        "EventToPresent.scala",
        "DiscoverySuite.scala",
        "SuiteSortingReporter.scala",
        "ConcurrentDistributor.scala",
        "FilterReporter.scala",
        "SuiteResult.scala",
        "SuiteParam.scala",
        "NestedSuiteParam.scala",
        "TestSpec.scala",
        "DistributedTestRunnerSuite.scala",
        "SuiteResultHolder.scala",
        "Durations.scala",
        "TestSortingReporter.scala",
        "RunDoneListener.scala",
        "SbtDispatchReporter.scala",
        "FriendlyParamsTranslator.scala"
      ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/exceptions", "org/scalatest/exceptions",
      List(
        "StackDepthException.scala",
        "NotAllowedException.scala",
        "StackDepth.scala",
        "TestPendingException.scala",
        "TestCanceledException.scala",
        "ModifiableMessage.scala",
        "PayloadField.scala",
        "ModifiablePayload.scala",
        "TestFailedException.scala",
        "PropertyCheckFailedException.scala",
        "TableDrivenPropertyCheckFailedException.scala",
        "DuplicateTestNameException.scala",
        "TestRegistrationClosedException.scala",
        "GeneratorDrivenPropertyCheckFailedException.scala",
        "DiscardedEvaluationException.scala",
        "TimeoutField.scala"
      ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/time", "org/scalatest/time",
      List(
        "Now.scala",
        "Span.scala",
        "SpanSugar.scala",
        "Units.scala"
      ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/words", "org/scalatest/words",
      List(
        "TypeCheckWord.scala",
        "CompileWord.scala",
        "BehaveWord.scala",
        "ResultOfTaggedAsInvocation.scala",
        "ResultOfStringPassedToVerb.scala",
        "ShouldVerb.scala",
        "MustVerb.scala",
        "CanVerb.scala",
        "StringVerbBlockRegistration.scala",
        "ResultOfAfterWordApplication.scala",
        "RegexWithGroups.scala",
        "DefinedWord.scala",
        "ResultOfOnlyApplication.scala",
        "ResultOfTheSameInstanceAsApplication.scala",
        "EmptyWord.scala",
        "ReadableWord.scala",
        "WritableWord.scala",
        "ResultOfNotExist.scala",
        "ExistWord.scala",
        "ResultOfATypeInvocation.scala",
        "ResultOfAnTypeInvocation.scala",
        "SortedWord.scala",
        "ResultOfAtMostOneOfApplication.scala",
        "ResultOfValueWordApplication.scala",
        "ResultOfKeyWordApplication.scala",
        "ResultOfInOrderApplication.scala",
        "ResultOfInOrderOnlyApplication.scala",
        "ResultOfAllOfApplication.scala",
        "ResultOfTheSameElementsInOrderAsApplication.scala",
        "ResultOfTheSameElementsAsApplication.scala",
        "ResultOfNoneOfApplication.scala",
        "ResultOfAtLeastOneOfApplication.scala",
        "ResultOfOneOfApplication.scala",
        "ResultOfDefinedAt.scala",
        "ResultOfRegexWordApplication.scala",
        "ResultOfAnWordToAnMatcherApplication.scala",
        "ResultOfAnWordToBePropertyMatcherApplication.scala",
        "ResultOfAWordToAMatcherApplication.scala",
        "ResultOfAnWordToSymbolApplication.scala",
        "ResultOfAWordToBePropertyMatcherApplication.scala",
        "ResultOfAWordToSymbolApplication.scala",
        "ResultOfGreaterThanOrEqualToComparison.scala",
        "ResultOfLessThanOrEqualToComparison.scala",
        "ResultOfGreaterThanComparison.scala",
        "ResultOfLessThanComparison.scala",
        "ResultOfMessageWordApplication.scala",
        "ResultOfSizeWordApplication.scala",
        "ResultOfLengthWordApplication.scala",
        "ContainWord.scala",
        "NotWord.scala",
        "BeWord.scala",
        "HaveWord.scala",
        "IncludeWord.scala",
        "EndWithWord.scala",
        "StartWithWord.scala",
        "FullyMatchWord.scala",
        "MatcherWords.scala",
        "PleaseUseNoExceptionShouldSyntaxInstead.scala",
        "ResultOfOfTypeInvocation.scala",
        "ResultOfThrownByApplication.scala",
        "ResultOfBeWordForAnType.scala",
        "ResultOfBeWordForAType.scala",
        "MatchPatternWord.scala",
        "NoExceptionWord.scala",
        "SizeWord.scala",
        "LengthWord.scala",
        "ResultOfBeWordForNoException.scala",
        "ResultOfContainWord.scala",
        "ResultOfNotWordForAny.scala",
        "ResultOfTheTypeInvocation.scala",
        "ResultOfOneElementOfApplication.scala",
        "ResultOfAtLeastOneElementOfApplication.scala",
        "ResultOfNoElementsOfApplication.scala",
        "ResultOfAllElementsOfApplication.scala",
        "ResultOfInOrderElementsOfApplication.scala",
        "ResultOfAtMostOneElementOfApplication.scala",
        "AggregatingExpression.scala",
        "ContainingExpression.scala",
        "SequencingExpression.scala",
        "EqualityExpression.scala",
        "BeEqualEqualWord.scala"
      ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/enablers", "org/scalatest/enablers",
      List(
        "Containing.scala",
        "Aggregating.scala",
        "KeyMapping.scala",
        "ValueMapping.scala",
        "Sequencing.scala",
        "Sortable.scala",
        "Readability.scala",
        "Writability.scala",
        "Emptiness.scala",
        "Definition.scala",
        "Length.scala",
        "Existence.scala",
        "Size.scala",
        "Messaging.scala",
        "Collecting.scala",
        "package.scala"
      ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/prop", "org/scalatest/prop",
      List(
        //"Configuration.scala",
        //"Checkers.scala",
        //"PropertyChecks.scala",
        "Whenever.scala"
      ), targetDir) ++
    copyDir("scalatest/src/main/scala/org/scalatest/concurrent", "org/scalatest/concurrent",
      List(
        "ScalaFutures.scala",
        "Futures.scala",
        "PatienceConfiguration.scala",
        "AbstractPatienceConfiguration.scala",
        "ScaledTimeSpans.scala"
      ), targetDir)
  }

  def genTest(targetDir: File, version: String, scalaVersion: String): Seq[File] = {
    copyDir("scalatest-test/src/test/scala/org/scalatest", "org/scalatest",
      List(
        "AssertionsSpec.scala"
      ), targetDir)
  }

  def genResource(targetDir: File, version: String, scalaVersion: String): Seq[File] = {
    val sourceResourceFile = new File("scalatest/src/main/resources/org/scalatest/ScalaTestBundle.properties")
    val destResourceDir = new File(targetDir, "resources/org/scalatest")
    destResourceDir.mkdirs()
    val destResourceFile = new File(destResourceDir, "ScalaTestBundle.properties")
    copyFile(sourceResourceFile, destResourceFile)
    List(destResourceFile)
  }
}