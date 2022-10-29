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



curl 'http://localhost:8080/auth/email/magicLink?code=2966be25-8d92-4e7d-a1c1-7e6a6d2504b6' \
  -H 'Content-Type: application/json'