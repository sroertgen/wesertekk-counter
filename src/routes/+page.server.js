
let total = 0 // total visitors during event
let onSite = 0 // current visitors on site

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
		total += 1
		onSite += 1
		return { success: true }
	},

  reentry: async () => {
    onSite += 1
    return { success: true }
  },

	decrement: async () => {
    onSite -= 1
		return { success: true }
	},

	reset: async () => {
		total = 0
		onSite = 0
		return { success: true }
	}
}
