package org.dbpedia.spotlight.io.stanbol

import java.io.File
import org.dbpedia.spotlight.model.AnnotatedParagraph
import org.apache.commons.io.FileUtils
import org.dbpedia.spotlight.io.AnnotatedTextSource

class StanbolDBLGenerator {
	val uri_pre          = "http://fise.iks-project.eu/ontology/entity-reference URI"
	val resource_uri_pre = "http://dbpedia.org/resource/"
	val f:StringBuilder  = new StringBuilder
	var i:Integer        = 0
			  
	def createStanbolDBLFromTSVFile( readFileName: String, writeFileName: String  ) {
		val paragraphs                 = AnnotatedTextSource.fromOccurrencesFile(new File(readFileName))
		val filecontents:StringBuilder = new StringBuilder
		println( "Converting the TSV-File to stanbol standart and saving the txt files..." )
		paragraphs.map( p => saveBDLFile(p, writeFileName) )
		println( "Done. %s benchmark files saved.".format( i ) )
	}  

	def saveBDLFile( paragraph: AnnotatedParagraph, fileName: String ) {
		val result:StringBuilder = new StringBuilder
		i                       += 1
		val file                 = new File( fileName.concat( i.toString() ).concat( ".txt" ) )
		result.append( "= INPUT =\n %s\n= EXPECT =\n".format( paragraph.text.text ) ) 
		
		val expect = paragraph.occurrences.map( o => "Description: '%s' has to be found \n %s %s\n\n"
		  					.format(o.surfaceForm.name, uri_pre, resource_uri_pre + o.resource.uri.toString() ) )
		expect.map( line => result.append( line ) )
		FileUtils.writeStringToFile( file, result.toString )
	}
}
