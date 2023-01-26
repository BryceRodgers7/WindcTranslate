This is a basic RESTful web service written in Scala and requires jdk 11.  

It uses the Play Framework, MySQL, database-evolutions and Slick to implement a cache for an expensive operation 'gTranslate' which calls a public API (http://boredapi.com/activity). The gTranslate function 'translates' a word (any string) into a pseudo-random phrase returned by boredapi.com/activity, simulating a transformation of some small input into a larger output. The translated phrases are an 'activity' that a person can do, and they come with an activity category along with some other metadata. It accepts mutiple words to be translated in a single request, plus an optional 'activity type'  
Setup instructions:  

  -install jdk 11  
  -install sbt 1.3.3+  
  -install scala 2.13+  
  -install play 2.0+  
  -execute 'sbt run' cmd from root directory after clone  
    -play sample app should start running on default port. Visit http://localhost:9000/ to confirm  
    -test cache api with following cURL:   
      curl -H "Content-Type: application/json" -X POST http://localhost:9000/api/windctranslate/translate -d "{ "words": ["first", "SecondWord"], \"actType\": \"music\" }"  
    -api documentation for the two interesting routes: GET /api/windctranslate  and  POST /api/windctranslate/translate  
      -GET /api/windctranslate  returns the full cache of database objects. This is used by the server to check the db cache, while processing a POST request  
      -POST /api/windctranslate/translate  accepts a json object with a String array 'words' plus an optional String 'actType' (short for Activity Type)  
        -returns a JSON array of String pairs (a word and it's translated phrase) in same order as the 'words' input array.  
  
      expected result from cURL above:   
      [ {  
          "first": "<some music-related activity>"  
        },  
        {  
            "SecondWord": "<some music-related activity. Can possibly be same phrase as previous word."  
      } ]  



#todo:  
#re-request gTranslate if that exact translation already exists in cache  
#add proper logging  
