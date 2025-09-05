
let total = 0 // total visitors during event
let onSite = 0 // current visitors on site

// Queue to process counter operations sequentially
/** @type {Array<{execute: () => Promise<void> | void, resolve: (value?: any) => void}>} */
const operationQueue = [];
let isProcessing = false;

async function processQueue() {
	if (isProcessing || operationQueue.length === 0) return;

	isProcessing = true;

	while (operationQueue.length > 0) {
		const operation = operationQueue.shift();
		if (operation) {
			await operation.execute();
			operation.resolve();
		}
	}

	isProcessing = false;
}

/**
 * @param {() => Promise<void> | void} operationFn
 */
function queueOperation(operationFn) {
	return new Promise((resolve) => {
		operationQueue.push({
			execute: operationFn,
			resolve: resolve
		});
		processQueue();
	});
}

/** @type {import('./$types').PageServerLoad} */
export async function load() {
	return {
		total,
		onSite
	};
}

/** @type {import('./$types').Actions} */
export const actions = {
	increment: async () => {
		await queueOperation(() => {
			total += 1;
			onSite += 1;
		});
		return { success: true, total, onSite };
	},

	reentry: async () => {
		await queueOperation(() => {
			onSite += 1;
		});
		return { success: true, total, onSite };
	},

	decrement: async () => {
		await queueOperation(() => {
			onSite = Math.max(0, onSite - 1); // Prevent negative values
		});
		return { success: true, total, onSite };
	},

	reset: async () => {
		await queueOperation(() => {
			total = 0;
			onSite = 0;
		});
		return { success: true, total, onSite };
	}
}
