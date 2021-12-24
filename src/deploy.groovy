import groovy.json.JsonSlurper

@Grab('com.aestasit.infrastructure.sshoogr:sshoogr:0.9.28')
import static com.aestasit.infrastructure.ssh.DefaultSsh.*

// load server config
configs = yaml.load(new File('../resources/config.yaml').text)
// module name
def module = configs.module.name

// local jar path
def local_path = new JsonSlurper().parseText(new File('../resources/local.json').text)
// remote deploy path
def remote_path = new JsonSlurper().parseText(new File('../resources/remote.json').text)

def jar_name = {
    paths = local_path[module].split('\\\\')
    projectName = paths[paths.length - 1]
}

def host = configs.server.host
def port = configs.server.port
def username = configs.server.username
def password = configs.server.password

// upload
options.trustUnknownHosts = true
remoteSession('root:$password@$host:$port') {
    exec 'scp -r ' + local_path[module] + 'root@$host:$port:' + remote_path[project]
}


