#curl -H "Content-Type: application/json"  -d '{"name": "devops"}' localhost:8080/skills -v; echo
#curl -H "Content-Type: application/json"  -d '{"name": "devops"}' localhost:8080/skills -v; echo
#curl -H "Content-Type: application/json"  -d '{"name": "developer"}' localhost:8080/skills -v; echo
#curl -H "Content-Type: application/json"  -d '{"name": "java"}' localhost:8080/skills -v; echo
#curl localhost:8080/skills --fail; echo
#curl localhost:8080/skills/dev --fail; echo

#
#curl -X 'POST' 'http://localhost:8080/auth/email' \
#  -H 'Content-Type: application/json' \
#  -d '{"email": "sotcsa@gmail.com"}'

#curl 'http://localhost:8080/auth/email/magicLink?code=2966be25-8d92-4e7d-a1c1-7e6a6d2504b6' \
#  -H 'Content-Type: application/json'

curl 'http://localhost:8080/auth/jwt' \
  -H 'Content-Type: application/json' | jq

curl 'http://localhost:8080/user' \
  -H 'Content-Type: application/json' \
  -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoQGNrZXIuY29tIiwiZXhwIjoxNjY3MzQyODEyLCJpYXQiOjE2NjczMzkyMTJ9.7kFlOdaeSlSrbxkKs9lHjxe5Ekn53DbR65fH-TEyW3M' | jq