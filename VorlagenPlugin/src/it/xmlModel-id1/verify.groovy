def logFile = new File( basedir, 'build.log' )
assert logFile.exists()
content = logFile.text

assert !(content =~ /symbol: class (?!Embeddable)/)

assert content.contains( 'ausgabe: erzeugt\\id1\\janusAngular2Backend\\reposPage.java')

