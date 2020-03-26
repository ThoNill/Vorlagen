def logFile = new File( basedir, 'build.log' )
assert logFile.exists()
content = logFile.text

assert !(content =~ /symbol: class (?!Embeddable)/)

assert content.contains( 'erzeugt\\id3\\entities\\BeispielMandant.java')
assert content.contains( 'erzeugt\\id3\\interfaces\\IBeispielMandant.java')
