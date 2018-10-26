package data.exchange.center.ommp.util.rabbitmq;

public class RabbitmqInfo {

	public static final String HTTP = "http://";
	public static final String IP_ADDR_1 = "150.0.2.46";
	public static final String IP_ADDR_2 = "150.0.2.46";
	public static final String PORT = "15672";
	public static final String USERNAME = "sgyrabbitmq001";
	public static final String PASSWORD = "sgyrabbitmq001";
	/**
	 * Various random bits of information that describe the whole system.
	 */
	public static final String OVERVIEW = "/api/overview";
	/**
	 * Name identifying this RabbitMQ cluster.
	 */
	public static final String CLUSTER_NAME = "/api/cluster-name";
	/**
	 * A list of nodes in the RabbitMQ cluster.
	 */
	public static final String nodes = "/api/nodes";
	/**
	 * An individual node in the RabbitMQ cluster. Add "?memory=true" to get memory statistics, and "?binary=true" to get a breakdown of binary memory use (may be expensive if there are many small binaries in the system). 
	 */
	public static final String NODES_NAME = "/api/nodes/name";
	/**
	 * A list of extensions to the management plugin.
	 */
	public static final String EXTENSIONS = "/api/extensions";
	/**
	 * The server definitions for a given virtual host - exchanges, queues, bindings and policies. POST to upload an existing set of definitions. Note that:
    <p>The definitions are merged. Anything already existing on the server but not in the uploaded definitions is untouched.
    <p>Conflicting definitions on immutable objects (exchanges, queues and bindings) will cause an error.
    <p>Conflicting definitions on mutable objects will cause the object in the server to be overwritten with the object from the definitions.
    <p>In the event of an error you will be left with a part-applied set of definitions.
	For convenience you may upload a file from a browser to this URI (i.e. you can use multipart/form-data as well as application/json) in which case the definitions should be uploaded as a form field named "file". 
	 */
	public static final String DEFINITIONS_VHOST_S = "/api/definitions/%s";
	/**
	 * A list of all open connections.
	 */
	public static final String CONNECTIONS = "/api/connections";
	/**
	 * A list of all open connections in a specific vhost.
	 */
	public static final String VHOSTS_VHOST_CONNECTIONS_S = "/api/vhosts/%s/connections";
	/**
	 * An individual connection. DELETEing it will close the connection. Optionally set the "X-Reason" header when DELETEing to provide a reason. 
	 */
	public static final String CONNECTIONS_NAME_S = "/api/connections/%s";
	/**
	 * List of all channels for a given connection. 
	 */
	public static final String CONNECTIONS_NAME_CHANNELS_S = "/api/connections/%s/channels";
	/**
	 * A list of all open channels.
	 */
	public static final String CHANNELS = "/api/channels";
	/**
	 * A list of all open channels in a specific vhost.
	 */
	public static final String VHOSTS_VHOST_CHANNELS_S = "/api/vhosts/%s/channels";
	/**
	 * Details about an individual channel.
	 */
	public static final String CHANNELS_CHANNEL_S = "/api/channels/%s";
	/**
	 * A list of all consumers.
	 */
	public static final String CONSUMERS = "/api/consumers";
	/**
	 * A list of all consumers in a given virtual host.
	 */
	public static final String CONSUMERS_VHOST_S = "/api/consumers/%s";
	/**
	 * A list of all exchanges.
	 */
	public static final String EXCHANGES = "/api/exchanges";
	/**
	 * A list of all exchanges in a given virtual host.
	 */
	public static final String EXCHANGES_VHOST_S = "/api/exchanges/%s";
	/**
	 * An individual exchange. To PUT an exchange, you will need a body looking something like this:
		<p>{"type":"direct","auto_delete":false,"durable":true,"internal":false,"arguments":{}}
		<p>The type key is mandatory; other keys are optional.
		<p>When DELETEing an exchange you can add the query string parameter if-unused=true. This prevents the delete from succeeding if the exchange is bound to a queue or as a source to another exchange. 
	 */
	public static final String EXCHANGES_VHOST_NAME_S_S = "/api/exchanges/%s/%s";
	/**
	 * A list of all bindings in which a given exchange is the source.
	 */
	public static final String EXCHANGES_VHOST_NAME_BINDINGS_SOURCE_S_S = "/api/exchanges/%s/%s/bindings/source";
	/**
	 * A list of all bindings in which a given exchange is the destination.
	 */
	public static final String EXCHANGES_VHOST_NAME_BINDINGS_DESTINATION_S_S = "/api/exchanges/%s/%s/bindings/destination";
	/**
	 * Publish a message to a given exchange. You will need a body looking something like:
		<p>{"properties":{},"routing_key":"my key","payload":"my body","payload_encoding":"string"}
		<p>All keys are mandatory. The payload_encoding key should be either "string" (in which case the payload will be taken to be the UTF-8 encoding of the payload field) or "base64" (in which case the payload field is taken to be base64 encoded).
		<p>If the message is published successfully, the response will look like:
		<p>{"routed": true}
		<p>routed will be true if the message was sent to at least one queue.
		<p>Please note that the HTTP API is not ideal for high performance publishing; the need to create a new TCP connection for each message published can limit message throughput compared to AMQP or other protocols using long-lived connections. 
	 */
	public static final String EXCHANGES_VHOST_NAME_PUBLISH_S_S = "/api/exchanges/%s/%s/publish";
	/**
	 * A list of all queues.
	 */
	public static final String QUEUES = "/api/queues";
	/**
	 * A list of all queues in a given virtual host.
	 */
	public static final String QUEUES_VHOST_S = "/api/queues/%s";
	/**
	 * An individual queue. To PUT a queue, you will need a body looking something like this:
		<p>{"auto_delete":false,"durable":true,"arguments":{},"node":"rabbit@smacmullen"}
		<p>All keys are optional.
		<p>When DELETEing a queue you can add the query string parameters if-empty=true and / or if-unused=true. These prevent the delete from succeeding if the queue contains messages, or has consumers, respectively. 
	 */
	public static final String QUEUES_VHOST_NAME_S = "/api/queues/%s/name";
	/**
	 * A list of all bindings on a given queue.
	 */
	public static final String QUEUES_VHOST_NAME_BINDINGS_S_S = "/api/queues/%s/%s/bindings";
	/**
	 * Contents of a queue. DELETE to purge. Note you can't GET this.
	 */
	public static final String QUEUES_VHOST_NAME_CONTENTS_S_S = "/api/queues/%s/%s/contents";
	/**
	 * Actions that can be taken on a queue. POST a body like:
		<p>{"action":"sync"}
		<p>Currently the actions which are supported are sync and cancel_sync. 
	 */
	public static final String QUEUES_VHOST_NAME_ACTIONS_S_S = "/api/queues/%s/%s/actions";
	/**
	 * Get messages from a queue. (This is not an HTTP GET as it will alter the state of the queue.) You should post a body looking like:
		<P>{"count":5,"requeue":true,"encoding":"auto","truncate":50000}
    	<P>	count controls the maximum number of messages to get. You may get fewer messages than this if the queue cannot immediately provide them.
    	<P>	requeue determines whether the messages will be removed from the queue. If requeue is true they will be requeued - but their redelivered flag will be set.
    	<P>	encoding must be either "auto" (in which case the payload will be returned as a string if it is valid UTF-8, and base64 encoded otherwise), or "base64" (in which case the payload will always be base64 encoded).
    	<P>	If truncate is present it will truncate the message payload if it is larger than the size given (in bytes).
		<P>truncate is optional; all other keys are mandatory.
		<P>Please note that the get path in the HTTP API is intended for diagnostics etc - it does not implement reliable delivery and so should be treated as a sysadmin's tool rather than a general API for messaging. 
	 */
	public static final String QUEUES_VHOST_NAME_GET_S_S = "/api/queues/%s/%s/get";
	/**
	 * A list of all bindings.
	 */
	public static final String BINDINGS = "/api/bindings";
	/**
	 * A list of all bindings in a given virtual host.
	 */
	public static final String BINDINGS_VHOST_S = "/api/bindings/%s";
	/**
	 * A list of all bindings between an exchange and a queue. Remember, an exchange and a queue can be bound together many times! To create a new binding, POST to this URI. You will need a body looking something like this:
		<p>{"routing_key":"my_routing_key","arguments":{}}
		<p>All keys are optional. The response will contain a Location header telling you the URI of your new binding. 
	 */
	public static final String BINDINGS_VHOST_E_EXCHANGE_Q_QUEUE_S_S_S = "/api/bindings/%s/e/%s/q/%s";
	/**
	 * An individual binding between an exchange and a queue. The props part of the URI is a "name" for the binding composed of its routing key and a hash of its arguments.
	 */
	public static final String BINDINGS_VHOST_E_EXCHANGE_Q_QUEUE_PROPS_S_S_S = "/api/bindings/%s/e/%s/q/%s/props";
	/**
	 * A list of all bindings between two exchanges. Similar to the list of all bindings between an exchange and a queue, above. 
	 */
	public static final String BINDINGS_VHOST_E_SOURCE_E_DESTINATION_S_S_S = "/api/bindings/%s/e/%s/e/%s";
	/**
	 * An individual binding between two exchanges. Similar to the individual binding between an exchange and a queue, above. 
	 */
	public static final String BINDINGS_VHOST_E_SOURCE_E_DESTINATION_PROPS_S_S_S = "/api/bindings/%s/e/%s/e/%s/props";
	/**
	 * A list of all vhosts.
	 */
	public static final String VHOSTS = "/api/vhosts";
	/**
	 * An individual virtual host. As a virtual host usually only has a name, you do not need an HTTP body when PUTing one of these. To enable / disable tracing, provide a body looking like:
		<p>{"tracing":true}
	 */
	public static final String VHOSTS_NAME_S = "/api/vhosts/%s";
	/**
	 * A list of all permissions for a given virtual host.
	 */
	public static final String VHOSTS_NAME_PERMISSIONS_S = "/api/vhosts/%s/permissions";
	/**
	 * A list of all users.
	 */
	public static final String USERS = "/api/users";
	/**
	 * An individual user. To PUT a user, you will need a body looking something like this:
	{"password":"secret","tags":"administrator"}
	or:
	{"password_hash":"2lmoth8l4H0DViLaK9Fxi6l9ds8=", "tags":"administrator"}
	The tags key is mandatory. Either password or password_hash must be set. Setting password_hash to "" will ensure the user cannot use a password to log in. tags is a comma-separated list of tags for the user. Currently recognised tags are "administrator", "monitoring" and "management". 
	 */
	public static final String USERS_NAME_S = "/api/users/%s";
	/**
	 * A list of all permissions for a given user.
	 */
	public static final String USERS_USER_PERMISSIONS_S = "/api/users/%s/permissions";
	/**
	 * Details of the currently authenticated user.
	 */
	public static final String WHOAMI = "/api/whoami";
	/**
	 * A list of all permissions for all users.
	 */
	public static final String PERMISSIONS = "/api/permissions";
	/**
	 * An individual permission of a user and virtual host. To PUT a permission, you will need a body looking something like this:
		{"configure":".*","write":".*","read":".*"}
		All keys are mandatory.
	 */
	public static final String PERMISSIONS_VHOST_USER_S_S = "/api/permissions/%s/%s";
	/**
	 * A list of all parameters.
	 */
	public static final String PARAMETERS = "/api/parameters";
	/**
	 * A list of all parameters for a given component.
	 */
	public static final String PARAMETERS_COMPONENT_S = "/api/parameters/%s";
	/**
	 * A list of all parameters for a given component and virtual host.
	 */
	public static final String PARAMETERS_COMPONENT_VHOST_S_S = "/api/parameters/%s/%s";
	/**
	 * An individual parameter. To PUT a parameter, you will need a body looking something like this:
		{"vhost": "/","component":"federation","name":"local_username","value":"guest"}
	 */
	public static final String PARAMETERS_COMPONENT_VHOST_NAME_S_S_S = "/api/parameters/%s/%s/%s";
	/**
	 * A list of all policies.
	 */
	public static final String POLICIES = "/api/policies";
	/**
	 * A list of all policies in a given virtual host.
	 */
	public static final String POLICIES_VHOST_S = "/api/policies/%s";
	/**
	 * An individual policy. To PUT a policy, you will need a body looking something like this:
		{"pattern":"^amq.", "definition": {"federation-upstream-set":"all"}, "priority":0, "apply-to": "all"}
		pattern and definition are mandatory, priority and apply-to are optional. 
	 */
	public static final String POLICIES_VHOST_NAME_S_S = "/api/policies/%s/%s";
	/**
	 * Declares a test queue, then publishes and consumes a message. Intended for use by monitoring tools. If everything is working correctly, will return HTTP status 200 with body:
		{"status":"ok"}
		Note: the test queue will not be deleted (to to prevent queue churn if this is repeatedly pinged). 
	 */
	public static final String ALIVENESS_TEST_VHOST_S = "/api/aliveness-test/%s";
	/**
	 * Runs basic healthchecks in the current node. Checks that the rabbit application is running, channels and queues can be listed successfully, and that no alarms are in effect. If everything is working correctly, will return HTTP status 200 with body:
		{"status":"ok"}
		If something fails, will return HTTP status 200 with the body of
		{"status":"failed","reason":"string"}
	 */
	public static final String HEALTHCHECKS_NODE = "/api/healthchecks/node";
	/**
	 * Runs basic healthchecks in the given node. Checks that the rabbit application is running, list_channels and list_queues return, and that no alarms are raised. If everything is working correctly, will return HTTP status 200 with body:
		{"status":"ok"}
		If something fails, will return HTTP status 200 with the body of
		{"status":"failed","reason":"string"}
	 */
	public static final String HEALTHCHECKS_NODE_NODE_S = "/api/healthchecks/node/%s";
}
