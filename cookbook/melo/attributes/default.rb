# nexus
default['cbp']['apps']['jee']['melo']['version'] = '0.1.0'
default['cbp']['apps']['jee']['melo']['packaging'] = "war"
default['cbp']['apps']['jee']['melo']['groupId'] = "cbp.production"
default['cbp']['apps']['jee']['melo']['artifactId'] = "melo"

# global cbp
default['cbp']['apps']['jee']['melo']['type'] = "web"
default['cbp']['apps']['jee']['melo']['jndi']['binding'] = ['melo.xml']

# logs
default['cbp']['apps']['jee']['melo']['template']['path']['log4j.xml'] = "WEB-INF/classes"
default['cbp']['apps']['jee']['melo']['traces']['level'] = 'INFO'
default['cbp']['apps']['jee']['melo']['troubleshooting']['level'] = 'WARN'