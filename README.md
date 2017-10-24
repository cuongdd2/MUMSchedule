## How to run
  + Go to src/main/java/app/Application class, run main() method
  + API will run on port 9860
## How to call api
  + API can be requested at URL http://localhost:9860/api/...
  + For example: POST http://localhost:9860/api/prof/submit to submit new professor
  + Request body needs : professor name
  
## How to change DB
  + We use MySQL at: "jdbc:mysql://104.207.139.224:3306/cs425"
  + user: cs425, pass: mum
  + table structure: src/main/sql/mum_schedule.sql
  + Please note what you have changed and the reason.

## How to submit HTML, CSS, JS
  + Put all asset (HTML, CSS, JS) to src/main/resources folder
  
## Example, tutorial???
  + See https://github.com/tipsy/spark-basic-structure
  
## Implementation
  + Create course first, it depends on itself only
  + Create entry, block, they based on datetime only
  + Create professor, them depends on course and block
  + Create student, they depends on entry, track(FPP, MPP)
  