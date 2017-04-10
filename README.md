# tech-talk
REST In Peace

REQUIREMENTS:
Running node.js installation

1) Create new Project
2) Add all required dependencies
    - Apollo Gradle Plugin
    - Apollo converter

Head over to the terminal
3) npm install -g apollo-codegen
4) apollo-codegen download-schema https://api.graph.cool/simple/v1/cj15clqsb0g1n0118rncsymy7 --output schema.json
5) Put this file in app/src/main/graphql/ .. put your schema.json here

6) Rebuild


Features:
- See upcoming TechTalks
- See Past TechTalks
- Notification for TechTalk about topic

Inside TechTalk detail:
- See Attachments
- Give rating
- ...

tech-talk App:
tech-talk
  date
  room
  topic
  speaker
  presentation

speaker/User
  name
  specialism
  rating
