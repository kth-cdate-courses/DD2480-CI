import axios from "axios"
import type { Contributor } from "./github.commit.types"

export async function getContributors() {
  return (
    await axios.get(
      "https://api.github.com/repos/kth-cdate-courses/DD2480-ci/contributors",
      {
        headers: {
          Authorization: `Bearer ${import.meta.env.GITHUB_ACCESS_TOKEN}`,
        },
      }
    )
  ).data as Contributor[]
}
