def logFile = new File( basedir, 'build.log' )
assert logFile.exists()
content = logFile.text

assert !(content =~ /symbol: class (?!Embeddable)/)

assert content.contains( 'erzeugt\\id2\\entities\\BeispielBuchung.java')
assert content.contains( 'erzeugt\\id2\\interfaces\\IBeispielBuchung.java')
