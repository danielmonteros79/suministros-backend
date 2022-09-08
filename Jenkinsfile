#!groovy
@Library ('workflowlibs_devops_ar@tags/2.0') _
run(){
	architecture = 'spring'
	pipeline = {
		vars = [
			execute: 'false',
			artifactory_repo_releases: 'ar-asum-aplicacion-suministros-mvn',
			artifactory_repo_snapshots: 'ar-asum-aplicacion-suministros-mvn-dev',
			vtrack_namespace: 'ar.asum.app-id-1332466.dsg',
		]
	}
	email = { object ->
		from='noreply@bbva.com'
		to='mariasol.perezfreire@bbva.com'
	}
}
