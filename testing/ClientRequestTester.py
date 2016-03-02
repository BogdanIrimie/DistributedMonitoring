from locust import HttpLocust, TaskSet, task

tlsJson = """
{
   "clientId":"13",
   "command":"security tls google.com",
   "responseAddress":"http://104.155.61.52:8008/jobFinished",
   "processors":[
      "XmlToJsonConverter",
      "security.TlsCiphersuitesFilter"
   ],
   "adapter":"adapters.EventHubAdapter"
}
"""

ecrypt2lvlJson = """
{
   "clientId":"13",
   "command":"security ecrypt2lvl google.com",
   "responseAddress":"http://104.155.61.52:8008/jobFinished",
   "processors":[
      "XmlToJsonConverter",
      "security.TlsCiphersuitesFilter",
      "security.TlsEcrypt2Level"
   ],
   "adapter":"adapters.EventHubAdapter"
}
"""

openPortsJson = """
{
    "clientId":"13",
    "command":"security ecrypt2lvl google.com",
    "responseAddress":"http://104.155.61.52:8008/jobFinished",
    "processors":[
        "XmlToJsonConverter",
        "security.TlsCiphersuitesFilter",
        "security.TlsEcrypt2Level"
    ],
    "adapter":"adapters.EventHubAdapter"
}
"""

# There will be three types of request that can be randomly sent with the same probability
class MyTaskSet(TaskSet):
    @task(1)
    def tlsRequest(l):
        l.client.post("/request", tlsJson)

    @task(1)
    def ecrypt2lvlRequest(l):
        l.client.post("/request", ecrypt2lvlJson)

    @task(1)
    def openPortsRequest(l):
        l.client.post("/request", openPortsJson)

class MyLocust(HttpLocust):
    task_set = MyTaskSet
    min_wait = 1000
    max_wait = 1500
